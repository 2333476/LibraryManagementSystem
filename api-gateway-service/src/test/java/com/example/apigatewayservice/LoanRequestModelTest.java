package com.example.apigatewayservice;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRequestModelTest {
    @Test
    void testAllSettersAndGetters() {
        LoanRequestModel loanRequest = new LoanRequestModel();

        loanRequest.setLoanDate(LocalDate.of(2024, 1, 1));
        loanRequest.setReturnDate(LocalDate.of(2024, 2, 1));
        loanRequest.setEmployeeId("emp123");
        loanRequest.setCustomerId("cust456");
        loanRequest.setBookId("book789");

        assertEquals(LocalDate.of(2024, 1, 1), loanRequest.getLoanDate());
        assertEquals(LocalDate.of(2024, 2, 1), loanRequest.getReturnDate());
        assertEquals("emp123", loanRequest.getEmployeeId());
        assertEquals("cust456", loanRequest.getCustomerId());
        assertEquals("book789", loanRequest.getBookId());
    }
}
