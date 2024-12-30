package com.example.hotel.communicator;

import com.example.hotel.exceptions.HttpRatingServiceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class RatingServiceCommunicator {
    private final RestTemplate restTemplate;

    @Autowired
    public RatingServiceCommunicator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    public long getRating(String id) {
        String url = "http://localhost:8081/rating/id/" + id;
       // ResponseEntity<Long> responseEntity = restTemplate.getForEntity(url+id, Long.class);
       // return responseEntity.getBody();
        ResponseEntity<Long> ratingResponse = restTemplate.exchange(url,HttpMethod.GET,null,Long.class);
        return ratingResponse.getBody();


    }

    public void addRating(Map<String, Long> ratingsMap) {
        String url = "http://localhost:8081/rating/add";
        //restTemplate.postForObject(url, ratingsMap, Objects.class);
        HttpEntity<Map<String ,Long>> requestEntity = new HttpEntity(ratingsMap);
        restTemplate.exchange(url, HttpMethod.POST,requestEntity,Object.class);
    }
    public void updateRating(Map<String, Long> ratingsMap) {
        String url = "http://localhost:8081/rating/update";
        //restTemplate.postForObject(url, ratingsMap, Objects.class);
        HttpEntity<Map<String ,Long>> requestEntity = new HttpEntity(ratingsMap);
        restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Object.class);
    }
    public void deleteRating(String id) {
        String url = "http://localhost:8081/rating/id/" + id;
        try {
            restTemplate.exchange(url,HttpMethod.DELETE,null,Object.class);
        }
        catch (HttpClientErrorException e) {
            throw new HttpRatingServiceNotFound(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

    }
}
