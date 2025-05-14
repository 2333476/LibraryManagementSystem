package com.example.loanservice.businesslayer;

import com.example.loanservice.presentationlayer.LoanRequestModel;
import com.example.loanservice.presentationlayer.LoanResponseModel;

import java.util.List;

public interface LoanService {

    LoanResponseModel getLoanByLoanId(String loanId);

    List<LoanResponseModel> getAllLoans();

    LoanResponseModel addLoan(LoanRequestModel requestModel);
    LoanResponseModel updateLoan(String loanId, LoanRequestModel requestModel);
    void deleteLoan(String loanId);

}
