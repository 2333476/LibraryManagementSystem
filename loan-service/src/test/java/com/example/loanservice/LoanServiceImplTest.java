package com.example.loanservice;

import com.example.loanservice.businesslayer.LoanServiceImpl;
import com.example.loanservice.datalayer.*;
import com.example.loanservice.datamapperlayer.LoanRequestMapper;
import com.example.loanservice.datamapperlayer.LoanResponseModelMapper;
import com.example.loanservice.datamapperlayer.bookdto.BookResponseModel;
import com.example.loanservice.domainclientlayer.BookServiceClient;
import com.example.loanservice.domainclientlayer.CustomerServiceClient;
import com.example.loanservice.domainclientlayer.EmployeeServiceClient;
import com.example.loanservice.presentationlayer.LoanRequestModel;
import com.example.loanservice.presentationlayer.LoanResponseModel;
import com.example.loanservice.utils.exceptions.NotFoundException;
import com.example.loanservice.utils.exceptions.OverdueLoanModificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest {

    @Mock(lenient = true)
    private LoanRepository loanRepository;
    @Mock(lenient = true)
    private LoanResponseModelMapper loanResponseModelMapper;
    @Mock(lenient = true)
    private LoanRequestMapper loanRequestMapper;
    @Mock(lenient = true)
    private BookServiceClient bookServiceClient;
    @Mock(lenient = true)
    private CustomerServiceClient customerServiceClient;
    @Mock(lenient = true)
    private EmployeeServiceClient employeeServiceClient;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setup() {
        loanService = new LoanServiceImpl(
                loanRepository,
                loanResponseModelMapper,
                bookServiceClient,
                customerServiceClient,
                employeeServiceClient,
                loanRequestMapper
        );
    }

    @Test
    @DisplayName("✖ Should throw NotFoundException when loan ID is invalid")
    void shouldThrowWhenLoanNotFound() {
        when(loanRepository.findByLoanIdentifier_LoanId("invalid-id")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                loanService.getLoanByLoanId("invalid-id")
        );
    }

    @Test
    @DisplayName("✖ Should throw OverdueLoanModificationException on update")
    void shouldThrowWhenUpdatingOverdueLoan() {
        Loan overdueLoan = Loan.builder()
                .loanIdentifier(new LoanIdentifier("overdue-1"))
                .bookIdentifier(new BookIdentifier("book"))
                .customerIdentifier(new CustomerIdentifier("cust"))
                .employeeIdentifier(new EmployeeIdentifier("emp"))
                .loanDate(LocalDate.of(2024, 1, 1))
                .returnDate(LocalDate.now().minusDays(5))
                .loanStatus(LoanStatus.OVERDUE)
                .penaltyAmount(5.0)
                .build();

        when(loanRepository.findByLoanIdentifier_LoanId("overdue-1")).thenReturn(Optional.of(overdueLoan));

        assertThrows(OverdueLoanModificationException.class, () ->
                loanService.updateLoan("overdue-1", mock(LoanRequestModel.class))
        );
    }

    @Test
    @DisplayName("✖ Should throw OverdueLoanModificationException on delete")
    void shouldThrowWhenDeletingOverdueLoan() {
        Loan overdueLoan = Loan.builder()
                .loanIdentifier(new LoanIdentifier("overdue-2"))
                .bookIdentifier(new BookIdentifier("book"))
                .customerIdentifier(new CustomerIdentifier("cust"))
                .employeeIdentifier(new EmployeeIdentifier("emp"))
                .loanDate(LocalDate.of(2024, 1, 1))
                .returnDate(LocalDate.now().minusDays(10))
                .loanStatus(LoanStatus.OVERDUE)
                .penaltyAmount(7.0)
                .build();

        when(loanRepository.findByLoanIdentifier_LoanId("overdue-2")).thenReturn(Optional.of(overdueLoan));

        assertThrows(OverdueLoanModificationException.class, () ->
                loanService.deleteLoan("overdue-2")
        );
    }

    @Test
    @DisplayName("✔ Should return loan when ID is valid")
    void shouldReturnLoanWhenIdIsValid() {
        String loanId = "valid-1";
        Loan loan = Loan.builder()
                .loanIdentifier(new LoanIdentifier(loanId))
                .bookIdentifier(new BookIdentifier("book-123"))
                .customerIdentifier(new CustomerIdentifier("cust-456"))
                .employeeIdentifier(new EmployeeIdentifier("emp-789"))
                .loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(7))
                .loanStatus(LoanStatus.ACTIVE)
                .penaltyAmount(0.0)
                .build();

        BookResponseModel mockBook = mock(BookResponseModel.class);
        when(mockBook.getTitle()).thenReturn("Mock Book Title");
        when(mockBook.getAuthor()).thenReturn("Mock Author");

        when(bookServiceClient.getBookByBookId("book-123")).thenReturn(mockBook);
        when(customerServiceClient.getCustomerByCustomerId("cust-456")).thenReturn(mock());
        when(employeeServiceClient.getEmployeeByEmployeeId("emp-789")).thenReturn(mock());

        LoanResponseModel mockResponse = mock(LoanResponseModel.class);
        LoanResponseModel.LoanResponseModelBuilder mockBuilder = mock(LoanResponseModel.LoanResponseModelBuilder.class);

        when(loanRepository.findByLoanIdentifier_LoanId(loanId)).thenReturn(Optional.of(loan));
        when(mockResponse.toBuilder()).thenReturn(mockBuilder);
        when(mockBuilder.bookTitle(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.bookAuthor(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.customerFullName(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.employeeFullName(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockResponse);
        when(loanResponseModelMapper.entityToResponseModel(any(Loan.class))).thenReturn(mockResponse);

        assertNotNull(loanService.getLoanByLoanId(loanId));
    }


    @Test
    @DisplayName("✔ Should delete loan successfully when not overdue")
    void shouldDeleteLoanSuccessfully() {
        String loanId = "loan-delete";
        Loan loan = Loan.builder()
                .loanIdentifier(new LoanIdentifier(loanId))
                .bookIdentifier(new BookIdentifier("book"))
                .customerIdentifier(new CustomerIdentifier("cust"))
                .employeeIdentifier(new EmployeeIdentifier("emp"))
                .loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(5))
                .loanStatus(LoanStatus.ACTIVE)
                .penaltyAmount(0.0)
                .build();

        when(loanRepository.findByLoanIdentifier_LoanId(loanId)).thenReturn(Optional.of(loan));

        loanService.deleteLoan(loanId);
    }
}