package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;

import java.util.List;

public interface LoanService {
    List<LoanResponseModel> getAllLoans();
    LoanResponseModel getLoanById(String loanId);
    LoanResponseModel addLoan(LoanRequestModel requestModel);
    void updateLoan(String loanId, LoanRequestModel requestModel);
    void deleteLoan(String loanId);
}
