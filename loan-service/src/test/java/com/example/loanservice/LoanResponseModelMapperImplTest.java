package com.example.loanservice;

import com.example.loanservice.datalayer.*;
import com.example.loanservice.datamapperlayer.LoanResponseModelMapperImpl;
import com.example.loanservice.presentationlayer.LoanResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoanResponseModelMapperImplTest {
    private LoanResponseModelMapperImpl mapper;

    @BeforeEach
    void setup() {
        mapper = new LoanResponseModelMapperImpl();
    }

    @Test
    void testEntityToResponseModelList() {
        // Given
        Loan loan1 = new Loan();
        loan1.setLoanIdentifier(new LoanIdentifier("L1"));
        loan1.setBookIdentifier(new BookIdentifier("B1"));
        loan1.setCustomerIdentifier(new CustomerIdentifier("C1"));
        loan1.setEmployeeIdentifier(new EmployeeIdentifier("E1"));
        loan1.setReturnDate(LocalDate.now());

        Loan loan2 = new Loan();
        loan2.setLoanIdentifier(new LoanIdentifier("L2"));
        loan2.setBookIdentifier(new BookIdentifier("B2"));
        loan2.setCustomerIdentifier(new CustomerIdentifier("C2"));
        loan2.setEmployeeIdentifier(new EmployeeIdentifier("E2"));
        loan2.setReturnDate(LocalDate.now());

        List<Loan> loanList = List.of(loan1, loan2);

        // When
        List<LoanResponseModel> result = mapper.entityToResponseModelList(loanList);

        // Then
        assertEquals(2, result.size());
        assertEquals("L1", result.get(0).getLoanId());
        assertEquals("L2", result.get(1).getLoanId());
    }
}
