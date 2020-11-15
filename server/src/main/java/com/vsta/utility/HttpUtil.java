package com.vsta.utility;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Http utility class
 * 
 */
@Service
public class HttpUtil {

  public static RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
    return restTemplate;
  }

  /**
   * Create a generic http request entity with content type of JSON
   * 
   * @param requestJson - Body of http request
   * @param apiKey
   * @return
   */
  public static HttpEntity<String> getHttpEntity(String requestJson, String apiKey) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Apikey", apiKey);
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
    return request;
  }

  /**
   * Http request factory Timeout is hard coded to 10 sec
   */
  public static ClientHttpRequestFactory getClientHttpRequestFactory() {
    int timeout = 10000;
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(timeout);
    return clientHttpRequestFactory;
  }
}
