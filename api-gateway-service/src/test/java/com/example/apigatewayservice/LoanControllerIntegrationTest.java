package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.LoanServiceClient;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private LoanServiceClient loanServiceClient;

    @Test
    void getAllLoans_returnsOkWithList() throws Exception {
        // Prepare mock data
        LoanResponseModel mockLoan = new LoanResponseModel(
                "loan123", "book789", "cust456", "emp321",
                LocalDate.of(2024, 5, 10),
                LocalDate.of(2024, 5, 20),
                false,
                "Java for Dummies", "John Author",
                "Alice Customer", "Bob Employee"
        );

        when(loanServiceClient.getAllLoans()).thenReturn(List.of(mockLoan));

        mockMvc.perform(get("/api/v1/loans")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].loanId").value("loan123"))
                .andExpect(jsonPath("$[0].bookId").value("book789"))
                .andExpect(jsonPath("$[0].bookTitle").value("Java for Dummies"))
                .andExpect(jsonPath("$[0].employeeFullName").value("Bob Employee"));
    }

}
