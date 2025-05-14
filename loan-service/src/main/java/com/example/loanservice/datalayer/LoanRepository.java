package com.example.loanservice.datalayer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    Optional<Loan> findByLoanIdentifier_LoanId(String loanId);
}
