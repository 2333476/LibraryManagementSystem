package com.example.apigatewayservice;

import com.example.apigatewayservice.businesslayer.LoanServiceImpl;
import com.example.apigatewayservice.domainclientlayer.LoanServiceClient;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoanServiceImplTest {

    private LoanServiceClient loanServiceClient;
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        loanServiceClient = mock(LoanServiceClient.class);
        loanService = new LoanServiceImpl(loanServiceClient);
    }

    @Test
    void getAllLoans_shouldReturnListOfLoans() {
        LoanResponseModel loan = new LoanResponseModel(
                "loan123", "book456", "cust789", "emp999",
                LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 15), false,
                "The Pragmatic Programmer", "Andy Hunt",
                "Alice Smith", "Bob Johnson"
        );

        List<LoanResponseModel> expectedList = List.of(loan);
        when(loanServiceClient.getAllLoans()).thenReturn(expectedList);

        List<LoanResponseModel> actualList = loanService.getAllLoans();

        assertEquals(expectedList, actualList);
        verify(loanServiceClient).getAllLoans();
    }

    @Test
    void getLoanById_shouldReturnLoan() {
        String loanId = "loan999";

        LoanResponseModel expected = new LoanResponseModel(
                loanId, "book123", "cust123", "emp123",
                LocalDate.of(2024, 4, 10), LocalDate.of(2024, 4, 25), true,
                "Clean Architecture", "Robert C. Martin",
                "Charlie Brown", "Dana White"
        );

        when(loanServiceClient.getLoanById(loanId)).thenReturn(expected);

        LoanResponseModel actual = loanService.getLoanById(loanId);

        assertEquals(expected, actual);
        verify(loanServiceClient).getLoanById(loanId);
    }

    @Test
    void addLoan_shouldReturnCreatedLoan() {
        LoanRequestModel request = new LoanRequestModel(
                "book789", "cust456", "emp789",
                LocalDate.of(2024, 5, 10), LocalDate.of(2024, 5, 25)
        );

        LoanResponseModel expected = new LoanResponseModel(
                "loan456", "book789", "cust456", "emp789",
                request.getLoanDate(), request.getReturnDate(), false,
                "Effective Java", "Joshua Bloch",
                "Emily Clark", "Frank Miller"
        );

        when(loanServiceClient.addLoan(request)).thenReturn(expected);

        LoanResponseModel actual = loanService.addLoan(request);

        assertEquals(expected, actual);
        verify(loanServiceClient).addLoan(request);
    }

    @Test
    void updateLoan_shouldCallUpdateMethod() {
        String loanId = "loan888";
        LoanRequestModel request = new LoanRequestModel(
                "book000", "cust000", "emp000",
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 15)
        );

        doNothing().when(loanServiceClient).updateLoan(loanId, request);

        loanService.updateLoan(loanId, request);

        verify(loanServiceClient).updateLoan(loanId, request);
    }

    @Test
    void deleteLoan_shouldCallDeleteMethod() {
        String loanId = "loan555";

        doNothing().when(loanServiceClient).deleteLoan(loanId);

        loanService.deleteLoan(loanId);

        verify(loanServiceClient).deleteLoan(loanId);
    }
}
