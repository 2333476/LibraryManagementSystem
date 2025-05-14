package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.domainclientlayer.LoanServiceClient;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanServiceClient loanServiceClient;

    public LoanServiceImpl(LoanServiceClient loanServiceClient) {
        this.loanServiceClient = loanServiceClient;
    }

    public List<LoanResponseModel> getAllLoans() {
        return loanServiceClient.getAllLoans();
    }

    public LoanResponseModel getLoanById(String loanId) {
        return loanServiceClient.getLoanById(loanId);
    }

    public LoanResponseModel addLoan(LoanRequestModel requestModel) {
        return loanServiceClient.addLoan(requestModel);
    }

    public void updateLoan(String loanId, LoanRequestModel requestModel) {
        loanServiceClient.updateLoan(loanId, requestModel);
    }

    public void deleteLoan(String loanId) {
        loanServiceClient.deleteLoan(loanId);
    }
}
