package com.todoService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
public class restTemplateService {

    @Autowired
    private final RestTemplate restTemplate;


    public restTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean checkToken(String token,String email) {
        String url = String.format("http://localhost:8081/api/auth/validate-token?email=%s", email);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Boolean.class
        );

        return response.getBody();
    }

    public int getUserId(String token,String email){
        String url = String.format("http://localhost:8081/api/users/id?email=%s", email);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Integer.class
        );

        return response.getBody();
    }

}
