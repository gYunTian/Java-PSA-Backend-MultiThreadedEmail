package com.portnet.quartz;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * Class definition to execute the scheduled API calls to portnet
 * 
 */

// to-do: refactor
@JsonInclude(Include.NON_DEFAULT)
@Service
public class QuartzJob implements Job {
    // Autowired is similar to importing class but in this is a auto dependecy
    // injection
    @Autowired
    private QuartzProperties prop;

    @Autowired
    private VesselService vesselService;

    // Main method to execute
    @Override
    public void execute(JobExecutionContext context) {
        // objects for sending post request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // set up request header
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Apikey", prop.getApiKey());
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        // create request body as json
        String requestJson = "{\"dateFrom\":\"" + prop.getDateFrom() + "\", \"dateTo\":\"" + prop.getDateTo() + "\"}";
        System.out.println("Sending Post request");

        // wrap heaader and body in HttpEntity object
        HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
        String res = null;

        try {
            res = restTemplate.postForObject(prop.getApiURL(), request, String.class);
        }
        catch (HttpStatusCodeException e ) {
            // non 200 status code
            System.out.println(e.getStatusCode().value());
        } 

        JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
        if (!jsonObject.get("errors").isJsonNull()) {
            System.out.println("Error in header or request body");
            return;
        }
        
        JsonArray jsonArray = jsonObject.getAsJsonArray("results");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Vessel> vessels = new ArrayList<>();
        
        if (jsonObject.get("errors").isJsonNull()) {
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
                    System.out.println("JSON Processing Error");
                    e.printStackTrace();
                }
                catch (NullPointerException e ) {
                    System.out.println(i+" Null pointer Error");
                    e.printStackTrace();
                }
                catch (Exception e ) {
                    System.out.println("Exception Error");
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Post request complete");
        
        // add to db
        System.out.println("Saving vessels to DB");
        vesselService.saveVessels(vessels);
        System.out.println("Cron complete");
    }   
}
