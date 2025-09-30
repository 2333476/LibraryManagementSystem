//package com.example.loanservice;
//
//import com.example.loanservice.businesslayer.LoanService;
//import com.example.loanservice.presentationlayer.LoanController;
//import com.example.loanservice.presentationlayer.LoanRequestModel;
//import com.example.loanservice.presentationlayer.LoanResponseModel;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(LoanController.class) // ✅ ONLY WebMvcTest here
//public class LoanControllerUnitTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private LoanService loanService; // ✅ Mock your service
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("✔ GET all loans returns 200 OK")
//    void getAllLoans_returnsOk() throws Exception {
//        LoanResponseModel loan = LoanResponseModel.builder()
//                .loanId("loan-1")
//                .bookId("book-1")
//                .customerId("cust-1")
//                .employeeId("emp-1")
//                .loanDate(LocalDate.now())
//                .returnDate(LocalDate.now().plusDays(30))
//                .isOverdue(false)
//                .bookTitle("Book Title")
//                .bookAuthor("Author Name")
//                .customerFullName("Jane Doe")
//                .employeeFullName("Mark Johnson")
//                .build();
//
//        Mockito.when(loanService.getAllLoans()).thenReturn(List.of(loan));
//
//        mockMvc.perform(get("/api/v1/loans"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].bookTitle").value("Book Title"));
//    }
//
//    @Test
//    @DisplayName("✔ GET loan by ID returns 200 OK")
//    void getLoanById_returnsOk() throws Exception {
//        String loanId = "loan-2";
//
//        LoanResponseModel loan = LoanResponseModel.builder()
//                .loanId(loanId)
//                .bookId("book-2")
//                .customerId("cust-2")
//                .employeeId("emp-2")
//                .loanDate(LocalDate.now())
//                .returnDate(LocalDate.now().plusDays(30))
//                .isOverdue(false)
//                .bookTitle("Clean Architecture")
//                .bookAuthor("Uncle Bob")
//                .customerFullName("John Smith")
//                .employeeFullName("Emily Davis")
//                .build();
//
//        Mockito.when(loanService.getLoanByLoanId(loanId)).thenReturn(loan);
//
//        mockMvc.perform(get("/api/v1/loans/{loanId}", loanId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.bookTitle").value("Clean Architecture"));
//    }
//
//    @Test
//    @DisplayName("✔ POST new loan returns 201 Created")
//    void addLoan_returnsCreated() throws Exception {
//        LoanRequestModel request = new LoanRequestModel(
//                "book-3", "cust-3", "emp-3",
//                LocalDate.now(), LocalDate.now().plusDays(30)
//        );
//
//        LoanResponseModel response = LoanResponseModel.builder()
//                .loanId("loan-3")
//                .bookId("book-3")
//                .customerId("cust-3")
//                .employeeId("emp-3")
//                .loanDate(request.getLoanDate())
//                .returnDate(request.getReturnDate())
//                .isOverdue(false)
//                .bookTitle("Refactoring")
//                .bookAuthor("Martin Fowler")
//                .customerFullName("Lisa Ray")
//                .employeeFullName("Alex Scott")
//                .build();
//
//        Mockito.when(loanService.addLoan(any(LoanRequestModel.class))).thenReturn(response);
//
//        mockMvc.perform(post("/api/v1/loans")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.loanId").value("loan-3"));
//    }
//
//    @Test
//    @DisplayName("✔ DELETE loan returns 204 No Content")
//    void deleteLoan_returnsNoContent() throws Exception {
//        Mockito.doNothing().when(loanService).deleteLoan("loan-4");
//
//        mockMvc.perform(delete("/api/v1/loans/{loanId}", "loan-4"))
//                .andExpect(status().isNoContent());
//    }
//}
