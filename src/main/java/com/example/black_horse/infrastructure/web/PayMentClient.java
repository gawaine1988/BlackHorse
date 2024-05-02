package com.example.black_horse.infrastructure.web;

import com.example.black_horse.infrastructure.web.apimodel.PaymentRequest;
import com.example.black_horse.infrastructure.web.apimodel.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PayMentClient {
    private RestTemplate restTemplate;

    public PayMentClient() {
        restTemplate = new RestTemplate();
    }

    public String getPaymentState(String tradeNo) throws JsonProcessingException {
        String paymentGetwayUrl
                = String.format("http://localhost:8082/payments?tradeNo=%s", tradeNo);
        ResponseEntity<String> response
                = restTemplate.getForEntity(paymentGetwayUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentResponse paymentResponse = objectMapper.readValue(response.getBody(), PaymentResponse.class);
        return paymentResponse.getTradeState();
    }

    public PaymentResponse createPayment(PaymentRequest request) throws JsonProcessingException {
        String fooResourceUrl
                = "http://localhost:8082/payments";
        ObjectMapper objectMapper = new ObjectMapper();
        String requestStr = objectMapper.writeValueAsString(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> strEntity = new HttpEntity<String>(requestStr,headers);

        String value = restTemplate.postForObject(fooResourceUrl, strEntity, String.class);

        PaymentResponse paymentResponse = objectMapper.readValue(value, PaymentResponse.class);
        return paymentResponse;
    }
}
