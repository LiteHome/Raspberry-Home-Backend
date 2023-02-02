package com.iot.rashome.commons.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.iot.rashome.commons.exception.IotBackendException;

public class HttpUtil {
    
    private static final RestTemplate restTemplate = new RestTemplate();


    public static String postJsonPayload(String url, String payload) throws IotBackendException, RestClientException, IllegalArgumentException {
        
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);

        
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw IotBackendException.parametersInMessage("Http 请求失败, 请求 Url 是 %s, 请求 payload 是 %s", url, payload);
        }

        return responseEntity.getBody();

    }
}
