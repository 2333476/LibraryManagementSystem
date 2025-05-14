package com.example.loanservice;

import com.example.loanservice.datalayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class LoanRepositoryIntegrationTest {

    @Autowired
    private LoanRepository loanRepository;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();

        loan = Loan.builder()
                .loanIdentifier(new LoanIdentifier("test-loan-id"))
                .bookIdentifier(new BookIdentifier("04c2e242-8d2f-4a1e-a7aa-b11a17d84f9e"))
                .customerIdentifier(new CustomerIdentifier("623e4567-e89b-12d3-a456-556642440005"))
                .employeeIdentifier(new EmployeeIdentifier("3b8afd72-75eb-4feb-bdb6-2471550cdf47"))
                .loanDate(LocalDate.of(2025, 5, 13))
                .returnDate(LocalDate.of(2025, 6, 13))
                .loanStatus(LoanStatus.ACTIVE)
                .penaltyAmount(0.0)
                .build();

        loanRepository.save(loan);
    }

    @Test
    @DisplayName("✔ Should find loan by loanId")
    void shouldFindLoanByLoanId() {
        Optional<Loan> result = loanRepository.findByLoanIdentifier_LoanId("test-loan-id");

        assertThat(result).isPresent();
        assertThat(result.get().getBookIdentifier().getBookId()).isEqualTo("04c2e242-8d2f-4a1e-a7aa-b11a17d84f9e");
    }

    @Test
    @DisplayName("✖ Should not find loan with invalid loanId")
    void shouldNotFindLoanByInvalidLoanId() {
        Optional<Loan> result = loanRepository.findByLoanIdentifier_LoanId("invalid-id");

        assertThat(result).isNotPresent();
    }
}

