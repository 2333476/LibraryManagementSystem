package com.example.apigatewayservice;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanResponseModelTest {
    @Test
    void testSettersAndGetters() {
        LoanResponseModel response = new LoanResponseModel();

        response.setLoanId("loan001");
        response.setEmployeeId("emp001");
        response.setEmployeeFullName("Alice Smith");
        response.setCustomerId("cust001");
        response.setCustomerFullName("Bob Jones");
        response.setBookId("book001");
        response.setBookTitle("Spring in Action");
        response.setBookAuthor("Craig Walls");
        response.setLoanDate(LocalDate.of(2024, 5, 1));
        response.setReturnDate(LocalDate.of(2024, 6, 1));
        response.setOverdue(true);

        assertEquals("loan001", response.getLoanId());
        assertEquals("emp001", response.getEmployeeId());
        assertEquals("Alice Smith", response.getEmployeeFullName());
        assertEquals("cust001", response.getCustomerId());
        assertEquals("Bob Jones", response.getCustomerFullName());
        assertEquals("book001", response.getBookId());
        assertEquals("Spring in Action", response.getBookTitle());
        assertEquals("Craig Walls", response.getBookAuthor());
        assertEquals(LocalDate.of(2024, 5, 1), response.getLoanDate());
        assertEquals(LocalDate.of(2024, 6, 1), response.getReturnDate());
        assertTrue(response.isOverdue());
    }
}
