package com.portnet.quartz;

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
import com.portnet.config.PropertiesReloader;
import com.portnet.entity.storage.Vessel;
import com.portnet.service.storage.VesselService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Class definition to execute the scheduled API calls to portnet and save the result to our database
 * to-do: refactor
 */

@JsonInclude(Include.NON_DEFAULT)
@Service
public class QuartzJob implements Job {

    @Autowired
    private PropertiesReloader propLoader;
    
    @Autowired
    private QuartzProperties prop;

    @Autowired
    private VesselService vesselService;

    // Main method to execute
    @Override
    public void execute(JobExecutionContext context) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = now.format(formatter);
        
        // reload properties file before executing job        
        if (prop.isEnabled()) {
            System.out.println(date +  "  - Quartz job: Executing job");
            // objects for sending post request
            HttpHeaders headers = new HttpHeaders();
            
            // set up request header
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Apikey", prop.getApiKey());
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            // create request body as json
            //String requestJson = "{\"dateFrom\":\"" + prop.getDateFrom() + "\", \"dateTo\":\"" + prop.getDateTo() + "\"}";
            String requestJson = "{\"dateFrom\":\"" + "2020-09-08" + "\", \"dateTo\":\"" + "2020-09-08" + "\"}";
            System.out.println(date +  "  - Quartz job: Sending Post request");
            
            // parse json array
            JsonArray jsonArray = PostAndParse(requestJson, headers);
            if (jsonArray == null) {
                return;
            }

            //extract values from json
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
                    vessels.add(mapper.readValue(temp.substring(0, temp.length() - 1)+",\"uniqueId\":\""+uniqueId+"\"}", Vessel.class));
                    
                } 
                catch (JsonProcessingException e) {
                    System.out.println(date +  "  - Quartz job: JSON Processing Error");
                    e.printStackTrace();
                }
                catch (NullPointerException e ) {
                    System.out.println(date +  "  - Quartz job: Null pointer Error");
                    e.printStackTrace();
                }
                catch (Exception e ) {
                    System.out.println(date +  "  - Quartz job: Exception Error");
                    e.printStackTrace();
                }
            }
            System.out.println(date +  "  - Quartz job: Post request complete");
            
            // add to db
            System.out.println(date +  "  - Quartz job: Saving vessels to DB");
            vesselService.saveVessels(vessels);

            

            System.out.println(date +  "  - Quartz job: Cron complete");
        }
        else {
            System.out.println(date + "  - Quartz Job disabled. If you want to run jobs, please enable it in reload.properties file" +
            " - quartz.properties.enabled = true");
        }
    }   

    public JsonArray PostAndParse(String requestJson, HttpHeaders headers) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
        JsonObject jsonObject = null;
        String res = null;
        JsonArray jsonArray = null;
        
        try {
            res = restTemplate.postForObject(prop.getApiURL(), request, String.class);
            jsonObject = new JsonParser().parse(res).getAsJsonObject();

            if (!jsonObject.get("errors").isJsonNull()) {
                System.out.println("Error in header or request body");
            }
            else {
                jsonArray = jsonObject.getAsJsonArray("results");
            }
        }
        catch (HttpStatusCodeException e ) {
            // non 200 status code
            System.out.println(e.getStatusCode().value());
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        
        return jsonArray;
    }
}
