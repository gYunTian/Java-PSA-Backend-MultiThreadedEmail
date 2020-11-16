package com.vsta.utility;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Http utility class. This class contains static methods that creates and
 * return objects required for working with HTTP requests
 */

@Service
public class HttpUtil {

  /**
   * This method creates a HTTP REST Template. This object is used to
   * invoke CRUD operations - i.e. POST, UPDATE, GET etc..
   * @return RestTemplate
   */
  public static RestTemplate getRestTemplate() {
    return new RestTemplate(getClientHttpRequestFactory());
  }

  /**
   * This method creates a generic http request entity. This object is used to
   * store the HTTP header and body. The default content type is JSON
   * @param requestJson Body of http request
   * @param apiKey API key
   * @return HttpEntity request
   */
  public static HttpEntity<String> getHttpEntity(String requestJson, String apiKey) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Apikey", apiKey);
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

    return new HttpEntity<String>(requestJson, headers);
  }

  /**
   * This method creates a Http request factory . This factory is used to store
   * configuration details such as timeout. In this case, HTTP timeout is hard
   * coded to 10 sec
   * @return ClientHttpRequestFactory
   */
  public static ClientHttpRequestFactory getClientHttpRequestFactory() {
    int timeout = 10000;
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(timeout);
    return clientHttpRequestFactory;
  }
}
