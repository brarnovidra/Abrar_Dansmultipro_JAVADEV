package com.dans.pro.mrbro.recruitment.service;

import com.dans.pro.mrbro.common.response.RestListResponse;
import com.dans.pro.mrbro.common.response.RestSingleResponse;
import com.dans.pro.mrbro.recruitment.payload.response.ListPositionV;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.dans.pro.mrbro.common.constant.ResultCode;
import com.dans.pro.mrbro.common.constant.ResultType;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.security.Keys;
@Slf4j
@Service
@RequiredArgsConstructor
public class PositionsService {
    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(PositionsService.class);

    @Value("${dans.baseUrl}")
    private String baseUrl;

    @Value("${dans.recruitment}")
    private String recruitment;

    public RestListResponse<ListPositionV.ListPositionResponseItem> Jobs() {
        String url = baseUrl+recruitment+"/positions.json";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            logger.error("Exception error: {}", e.getMessage());
            return RestListResponse.create(ResultCode.FAIL, ResultType.READ, null, "JOBSs ");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ListPositionV.ListPositionResponse listPositionResponse = new ListPositionV.ListPositionResponse();
        List<ListPositionV.ListPositionResponseItem> listPositionResponseItems = new ArrayList<>();
        try {
            // Parse the JSON string into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            // Assuming you have a ListPositionV.ListPositionResponseItem class
            listPositionResponseItems = objectMapper.readValue(
                    jsonNode.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ListPositionV.ListPositionResponseItem.class)
            );

            // Set the list of items in your response object
            listPositionResponse.setListPositionResponse(listPositionResponseItems);

            return RestListResponse.create(ResultCode.SUCCESS, ResultType.READ, listPositionResponseItems, "JOBS ");
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException error: {}", e.getMessage());
        }

        return RestListResponse.create(ResultCode.FAIL, ResultType.READ, null, "JOBS ");

    }

    public RestSingleResponse<ListPositionV.ListPositionResponseItem> JobsDetail(@PathVariable @NotBlank String jobId) {
        String url = baseUrl+recruitment+"/positions/"+jobId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ListPositionV.ListPositionResponseItem> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ListPositionV.ListPositionResponseItem.class);
        } catch (Exception e) {
            logger.error("Exception error: {}", e.getMessage());
            return RestSingleResponse.create(ResultCode.FAIL, ResultType.READ, null, "JOBS ");
        }


        return RestSingleResponse.create(ResultCode.SUCCESS, ResultType.READ, responseEntity.getBody(), "JOBS ");
    }
}
