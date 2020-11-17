package com.vsta.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.vsta.model.Vessel;
import com.vsta.service.VesselService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Quartz job service. This class contains methods required to perform project
 * functional requirement: 1), 2), 3)
 */

@JsonInclude(Include.NON_DEFAULT)
@Service
public class JobService {

  @Autowired
  private QuartzProperties prop;

  @Autowired
  private VesselService vesselService;

  /**
   * The main method that will be executed by the QuartJob. This method will send
   * a post request to PORTNET API, parse the returned JSON object and save the
   * result in database.
   */
  public void executeJob() {
    // reload properties file before executing job
    if (prop.isEnabled()) {
      printStatus("Executing job");

      // create request body
      // gets date range from reload.properties file
      String requestJson = "{\"dateFrom\":\"" + prop.getDateFrom() + "\", \"dateTo\":\"" + prop.getDateTo() + "\"}";
      // String requestJson = "{\"dateFrom\":\"" + "2020-11-08" + "\", \"dateTo\":\""
      // + "2020-11-10" + "\"}";
      // String requestJson = "{\"dateFrom\":\"" + "2020-09-08" + "\", \"dateTo\":\""
      // + "2020-09-14" + "\"}";

      printStatus("Sending Post request");
      // parse json array
      JsonArray jsonArray = PostAndParse(requestJson, prop.getApiKey());

      if (jsonArray == null) {
        printStatus("There was a problem with sending the post request!");
        return;
      }

      List<Vessel> vessels = new ArrayList<>();
      vessels = mapVessels(jsonArray);

      printStatus("Post request complete");
      printStatus("Saving vessels to DB");

      // add to db
      // for (Vessel vessel : vessels) {
      //   vesselService.saveVessel(vessel);
      // }
      vesselService.saveVessels(vessels);
      printStatus("Cron job complete");

    } else {
      printStatus(
          "Quartz Job disabled. If you want to run jobs, please enable it in reload.properties file by setting quartz.properties.enabled = true");
    }

  /**
   * A private and customized status printer.
   *
   * @param message
   */
  private void printStatus(String message) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String date = now.format(formatter);
    System.out.println(date + "  - Quartz job: " + message);

  }

  /**
   * A priavte and customized method that maps a JSON array to Java List of Vessel
   * type.
   *
   * @param jsonArray
   * @return List<Vessel>
   */
  private List<Vessel> mapVessels(JsonArray jsonArray) {
    // extract values from json
    ObjectMapper mapper = new ObjectMapper();
    List<Vessel> vessels = new ArrayList<>();
    int length = jsonArray.size();
    String uniqueId = null;
    String temp = null;

    for (int i = 0; i < length; i++) {
      try {
        // create uniqueIds for each vessel using fullVsLm and inVoyN
        uniqueId = (jsonArray.get(i).getAsJsonObject().get("fullVslM").getAsString() + " "
            + jsonArray.get(i).getAsJsonObject().get("inVoyN").getAsString());
        temp = jsonArray.get(i).toString();
        vessels.add(mapper.readValue(temp.substring(0, temp.length() - 1) + ",\"uniqueId\":\"" + uniqueId + "\"}",
            Vessel.class));

      } catch (JsonProcessingException e) {
        printStatus("JSON Processing Error");
        e.printStackTrace();
      } catch (NullPointerException e) {
        printStatus("Null pointer Error");
        e.printStackTrace();
      } catch (Exception e) {
        printStatus("Exception Error");
        e.printStackTrace();
      }
    }
    return vessels;
  }

}
