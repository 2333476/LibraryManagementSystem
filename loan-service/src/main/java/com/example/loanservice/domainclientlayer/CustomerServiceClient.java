package com.example.loanservice.domainclientlayer;

import com.example.loanservice.datamapperlayer.customerdto.CustomerResponseModel;
import com.example.loanservice.utils.exceptions.InvalidInputException;
import com.example.loanservice.utils.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpStatus.*;

@Component
@Slf4j
public class CustomerServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CUSTOMER_SERVICE_BASE_URL;

    public CustomerServiceClient(RestTemplate restTemplate,
                                 ObjectMapper objectMapper,
                                 @Value("${app.customer-service.host}") String host,
                                 @Value("${app.customer-service.port}") String port) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.CUSTOMER_SERVICE_BASE_URL = "http://" + host + ":" + port + "/api/v1/customers";
    }

    public CustomerResponseModel getCustomerByCustomerId(String customerId) {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL + "/" + customerId;
            System.out.println("[CustomerServiceClient] Calling URL: " + url);

            return restTemplate.getForObject(
                    CUSTOMER_SERVICE_BASE_URL + "/" + customerId,
                    CustomerResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            throw new InvalidInputException();
        } else if (ex.getStatusCode() == NOT_FOUND) {
            throw new NotFoundException();
        }
        return ex;
    }
}
