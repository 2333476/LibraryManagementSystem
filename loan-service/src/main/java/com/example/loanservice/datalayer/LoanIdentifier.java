package com.example.loanservice.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class LoanIdentifier {

    private String loanId;

    public LoanIdentifier() {
        this.loanId = UUID.randomUUID().toString();
    }

    public LoanIdentifier(String loanId) {
        this.loanId = loanId;
    }
}
