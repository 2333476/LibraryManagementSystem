package com.example.apigatewayservice.domainclientlayer;

import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CustomerServiceClient {

    private final RestTemplate restTemplate;
    private final String CUSTOMER_SERVICE_BASE_URL;

    public CustomerServiceClient(RestTemplate restTemplate,
                                  @Value("${app.customers-service.host}") String customersServiceHost,
                                  @Value("${app.customers-service.port}") String customersServicePort) {
        this.restTemplate = restTemplate;
        this.CUSTOMER_SERVICE_BASE_URL = "http://" + customersServiceHost + ":" + customersServicePort + "/api/v1/customers";
    }

    public List<CustomerResponseModel> getAllCustomers() {
        try {
            return restTemplate.exchange(
                    CUSTOMER_SERVICE_BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomerResponseModel>>() {}
            ).getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de l'appel à customer-service: " + e.getMessage());
        }
    }

    public CustomerResponseModel getCustomerById(String customerId) {
        try {
            return restTemplate.getForObject(
                    CUSTOMER_SERVICE_BASE_URL + "/" + customerId,
                    CustomerResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la récupération du client avec ID " + customerId + ": " + e.getMessage());
        }
    }

    public CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel) {
        try {
            return restTemplate.postForObject(
                    CUSTOMER_SERVICE_BASE_URL,
                    customerRequestModel,
                    CustomerResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de l'ajout du customer : " + e.getMessage());
        }
    }

    public CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel customerRequestModel) {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL + "/" + customerId;
            restTemplate.put(url, customerRequestModel);

            return getCustomerById(customerId);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du customer : " + e.getMessage());
        }
    }


    public String deleteCustomerById(String customerId) {
        try {
            restTemplate.delete(CUSTOMER_SERVICE_BASE_URL + "/" + customerId);
            return "Customer with ID " + customerId + " has been deleted successfully.";
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la suppression du client avec ID " + customerId + ": " + e.getMessage());
        }
    }

}
