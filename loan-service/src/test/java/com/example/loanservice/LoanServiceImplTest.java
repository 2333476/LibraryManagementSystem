package com.example.loanservice;

import com.example.loanservice.businesslayer.LoanServiceImpl;
import com.example.loanservice.datalayer.*;
import com.example.loanservice.datamapperlayer.LoanRequestMapper;
import com.example.loanservice.datamapperlayer.LoanResponseModelMapper;
import com.example.loanservice.domainclientlayer.BookServiceClient;
import com.example.loanservice.domainclientlayer.CustomerServiceClient;
import com.example.loanservice.domainclientlayer.EmployeeServiceClient;
import com.example.loanservice.presentationlayer.LoanRequestModel;
import com.example.loanservice.utils.exceptions.NotFoundException;
import com.example.loanservice.utils.exceptions.OverdueLoanModificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanServiceImplTest {

    private LoanRepository loanRepository;
    private LoanResponseModelMapper loanResponseModelMapper;
    private LoanRequestMapper loanRequestMapper;
    private BookServiceClient bookServiceClient;
    private CustomerServiceClient customerServiceClient;
    private EmployeeServiceClient employeeServiceClient;

    private LoanServiceImpl loanService;

    @BeforeEach
    void setup() {
        loanRepository = mock(LoanRepository.class);
        loanResponseModelMapper = mock(LoanResponseModelMapper.class);
        loanRequestMapper = mock(LoanRequestMapper.class);
        bookServiceClient = mock(BookServiceClient.class);
        customerServiceClient = mock(CustomerServiceClient.class);
        employeeServiceClient = mock(EmployeeServiceClient.class);

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
}
