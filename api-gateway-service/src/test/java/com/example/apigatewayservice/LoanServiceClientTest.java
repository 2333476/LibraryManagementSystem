package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.LoanServiceClient;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LoanServiceClient loanServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loanServiceClient = new LoanServiceClient(restTemplate, "localhost", "8085");
    }

    @Test
    void testGetAllLoans() {
        LoanResponseModel[] mockLoans = {
                new LoanResponseModel("loan1", "book1", "cust1", "emp1",
                        LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15),
                        false, "Book Title 1", "Author A", "Customer A", "Employee A"),
                new LoanResponseModel("loan2", "book2", "cust2", "emp2",
                        LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 15),
                        true, "Book Title 2", "Author B", "Customer B", "Employee B")
        };

        ResponseEntity<LoanResponseModel[]> response = new ResponseEntity<>(mockLoans, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(LoanResponseModel[].class))).thenReturn(response);

        List<LoanResponseModel> result = loanServiceClient.getAllLoans();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("loan1", result.get(0).getLoanId());
    }

    @Test
    void testGetLoanById() {
        String loanId = "loan123";
        LoanResponseModel mockLoan = new LoanResponseModel(
                loanId, "bookX", "custX", "empX",
                LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10),
                false, "Sample Book", "Sample Author", "John Smith", "Jane Doe"
        );

        when(restTemplate.getForObject(contains(loanId), eq(LoanResponseModel.class)))
                .thenReturn(mockLoan);

        LoanResponseModel result = loanServiceClient.getLoanById(loanId);

        assertNotNull(result);
        assertEquals(loanId, result.getLoanId());
        assertEquals("Sample Book", result.getBookTitle());
    }

    @Test
    void testAddLoan() {
        LoanRequestModel request = new LoanRequestModel(
                "book123", "cust123", "emp123",
                LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 15)
        );

        LoanResponseModel mockResponse = new LoanResponseModel(
                "loan789", "book123", "cust123", "emp123",
                request.getLoanDate(), request.getReturnDate(),
                false, "New Book", "New Author", "Alice", "Bob"
        );

        when(restTemplate.postForObject(anyString(), eq(request), eq(LoanResponseModel.class)))
                .thenReturn(mockResponse);

        LoanResponseModel result = loanServiceClient.addLoan(request);

        assertNotNull(result);
        assertEquals("loan789", result.getLoanId());
    }

    @Test
    void testUpdateLoan() {
        String loanId = "loan321";
        LoanRequestModel request = new LoanRequestModel(
                "book456", "cust456", "emp456",
                LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 20)
        );

        doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(restTemplate).exchange(
                contains(loanId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Void.class)
        );

        assertDoesNotThrow(() -> loanServiceClient.updateLoan(loanId, request));
    }

    @Test
    void testDeleteLoan() {
        String loanId = "loan999";

        doReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)).when(restTemplate).exchange(
                contains(loanId),
                eq(HttpMethod.DELETE),
                isNull(),
                eq(Void.class)
        );

        assertDoesNotThrow(() -> loanServiceClient.deleteLoan(loanId));
    }
}
