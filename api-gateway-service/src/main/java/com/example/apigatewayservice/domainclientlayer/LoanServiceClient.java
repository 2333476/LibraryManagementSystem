package com.example.apigatewayservice.domainclientlayer;

import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class LoanServiceClient {

    private final RestTemplate restTemplate;
    private final String LOAN_BASE_URL;

    public LoanServiceClient(RestTemplate restTemplate,
                             @Value("${app.loan-service.host}") String loanServiceHost,
                             @Value("${app.loan-service.port}") String loanServicePort) {
        this.restTemplate = restTemplate;
        this.LOAN_BASE_URL = "http://" + loanServiceHost + ":" + loanServicePort + "/api/v1/loans";
    }

    public List<LoanResponseModel> getAllLoans() {
        try {
            ResponseEntity<LoanResponseModel[]> response = restTemplate.getForEntity(
                    LOAN_BASE_URL,
                    LoanResponseModel[].class
            );
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la récupération des prêts : " + e.getMessage());
        }
    }

    public LoanResponseModel getLoanById(String loanId) {
        try {
            return restTemplate.getForObject(
                    LOAN_BASE_URL + "/" + loanId,
                    LoanResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la récupération du prêt : " + e.getMessage());
        }
    }

    public LoanResponseModel addLoan(LoanRequestModel request) {
        try {
            return restTemplate.postForObject(
                    LOAN_BASE_URL,
                    request,
                    LoanResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de l'ajout d’un prêt : " + e.getMessage());
        }
    }

    public void updateLoan(String loanId, LoanRequestModel request) {
        try {
            restTemplate.exchange(
                    LOAN_BASE_URL + "/" + loanId,
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du prêt : " + e.getMessage());
        }
    }

    public void deleteLoan(String loanId) {
        try {
            restTemplate.exchange(
                    LOAN_BASE_URL + "/" + loanId,
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la suppression du prêt : " + e.getMessage());
        }
    }
}
