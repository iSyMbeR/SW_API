package com.example.sw_api.healthCheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "test-swapi.dev-connection")
public class TestConnectionWithExternalApi {
    Logger logger = LoggerFactory.getLogger(TestConnectionWithExternalApi.class);
    private static final String URL = "https://swapi.dev/";

    @ReadOperation
    public Map<String, String> getSwApi() {
        Map<String, String> connectionMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            connectionMap.put("status", "ok");
            logger.info("Connected with external API " + URL + "status ok");
        } catch (Exception e) {
            connectionMap.put("status", "fail");
            logger.warn("Couldn't connect with external API " + URL + "status fail");
        }
        return connectionMap;
    }
}
