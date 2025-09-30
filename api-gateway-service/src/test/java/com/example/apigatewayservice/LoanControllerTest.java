package com.example.apigatewayservice;
import com.example.apigatewayservice.businesslayer.LoanService;
import com.example.apigatewayservice.presentationlayer.LoanController;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanControllerTest {
    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loanController = new LoanController(loanService);
    }

    @Test
    void testGetAllLoans() {
        List<LoanResponseModel> mockList = List.of(
                new LoanResponseModel("loan1", "book1", "cust1", "emp1",
                        LocalDate.now(), LocalDate.now().plusDays(7),
                        false, "Book A", "Author A", "John Doe", "Jane Smith")
        );

        when(loanService.getAllLoans()).thenReturn(mockList);

        List<LoanResponseModel> result = loanController.getAllLoans();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loanService).getAllLoans();
    }

    @Test
    void testGetLoanById() {
        String loanId = "loan42";
        LoanResponseModel mockResponse = new LoanResponseModel(
                loanId, "book2", "cust2", "emp2",
                LocalDate.now(), LocalDate.now().plusDays(5),
                false, "Book B", "Author B", "Alice", "Bob"
        );

        when(loanService.getLoanById(loanId)).thenReturn(mockResponse);

        LoanResponseModel result = loanController.getLoanById(loanId);

        assertNotNull(result);
        assertEquals(loanId, result.getLoanId());
        verify(loanService).getLoanById(loanId);
    }

    @Test
    void testAddLoan() {
        LoanRequestModel request = new LoanRequestModel(
                "book3", "cust3", "emp3",
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 10)
        );

        LoanResponseModel mockResponse = new LoanResponseModel(
                "loan99", "book3", "cust3", "emp3",
                request.getLoanDate(), request.getReturnDate(),
                false, "Book C", "Author C", "Charlie", "Eve"
        );

// ✅ Proper way to mock a non-void method
        when(loanService.addLoan(request)).thenReturn(mockResponse);

        ResponseEntity<Map<String, String>> response = loanController.addLoan(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("✅ Loan successfully added.", response.getBody().get("message"));

        verify(loanService).addLoan(request);
    }

    @Test
    void testUpdateLoan() {
        String loanId = "loan-update";
        LoanRequestModel request = new LoanRequestModel(
                "bookX", "custX", "empX",
                LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 15)
        );

        doNothing().when(loanService).updateLoan(loanId, request);

        loanController.updateLoan(loanId, request);

        verify(loanService).updateLoan(loanId, request);
    }

    @Test
    void testDeleteLoan() {
        String loanId = "loan-delete";

        doNothing().when(loanService).deleteLoan(loanId);

        loanController.deleteLoan(loanId);

        verify(loanService).deleteLoan(loanId);
    }
}
