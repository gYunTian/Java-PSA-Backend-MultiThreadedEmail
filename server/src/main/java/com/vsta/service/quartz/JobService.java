package com.vsta.service.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vsta.entity.storage.Vessel;
import com.vsta.quartz.QuartzProperties;
import com.vsta.service.storage.VesselService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Quartz job service that executes the actual underlying task
 */

@JsonInclude(Include.NON_DEFAULT)
@Service
public class JobService {

  @Autowired
  private QuartzProperties prop;

  @Autowired
  private VesselService vesselService;

  public void executeJob() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String date = now.format(formatter);

    // reload properties file before executing job
    if (prop.isEnabled()) {
      System.out.println(date + "  - Quartz job: Executing job");
      // objects for sending post request
      HttpHeaders headers = new HttpHeaders();

      // set up request header
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.add("Apikey", prop.getApiKey());
      headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

      // create request body as json
      String requestJson = "{\"dateFrom\":\"" + prop.getDateFrom() + "\", \"dateTo\":\"" + prop.getDateTo() + "\"}";
    //   String requestJson = "{\"dateFrom\":\"" + "2020-11-05" + "\", \"dateTo\":\"" + "2020-11-11" + "\"}";
      System.out.println(date + "  - Quartz job: Sending Post request");

      // parse json array
      JsonArray jsonArray = PostAndParse(requestJson, headers);
      if (jsonArray == null) {
        return;
      }

      // extract values from json
      ObjectMapper mapper = new ObjectMapper();
      List<Vessel> vessels = new ArrayList<>();
      int length = jsonArray.size();
      String uniqueId = null;
      String temp = null;

      for (int i = 0; i < length; i++) {
        try {

          uniqueId = (jsonArray.get(i).getAsJsonObject().get("fullVslM").getAsString() + " "
              + jsonArray.get(i).getAsJsonObject().get("inVoyN").getAsString());
          temp = jsonArray.get(i).toString();
          vessels.add(mapper.readValue(temp.substring(0, temp.length() - 1) + ",\"uniqueId\":\"" + uniqueId + "\"}",
              Vessel.class));

        } catch (JsonProcessingException e) {
          System.out.println(date + "  - Quartz job: JSON Processing Error");
          e.printStackTrace();
        } catch (NullPointerException e) {
          System.out.println(date + "  - Quartz job: Null pointer Error");
          e.printStackTrace();
        } catch (Exception e) {
          System.out.println(date + "  - Quartz job: Exception Error");
          e.printStackTrace();
        }
      }
      System.out.println(date + "  - Quartz job: Post request complete");

      // add to db
      System.out.println(date + "  - Quartz job: Saving vessels to DB");
      vesselService.saveVessels(vessels);

      System.out.println(date + "  - Quartz job: Cron complete");
    } else {
      System.out
          .println(date + "  - Quartz Job disabled. If you want to run jobs, please enable it in reload.properties file"
              + " - quartz.properties.enabled = true");
    }
  }

  public JsonArray PostAndParse(String requestJson, HttpHeaders headers) {
    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
    HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
    JsonObject jsonObject = null;
    String res = null;
    JsonArray jsonArray = null;

    try {
      res = restTemplate.postForObject(prop.getApiURL(), request, String.class);

      jsonObject = new JsonParser().parse(res).getAsJsonObject();

      if (!jsonObject.get("errors").isJsonNull()) {
        System.out.println("Error in header or request body");
      } else {
        jsonArray = jsonObject.getAsJsonArray("results");
      }
    } catch (ResourceAccessException e) {
      System.out.println("Connection timed out: " + e);
    } catch (HttpStatusCodeException e) {
      // non 200 status code
      System.out.println(e.getStatusCode().value());
    } catch (Exception e) {
      System.out.println(e);
    }

    return jsonArray;
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory() {
    int timeout = 10000;
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(timeout);
    return clientHttpRequestFactory;
  }
}
