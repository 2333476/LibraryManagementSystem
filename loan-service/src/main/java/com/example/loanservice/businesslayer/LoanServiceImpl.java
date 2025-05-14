package com.example.loanservice.businesslayer;

import com.example.loanservice.datalayer.Loan;
import com.example.loanservice.datalayer.LoanIdentifier;
import com.example.loanservice.datalayer.LoanRepository;
import com.example.loanservice.datamapperlayer.LoanRequestMapper;
import com.example.loanservice.datamapperlayer.LoanResponseModelMapper;
import com.example.loanservice.datamapperlayer.bookdto.BookResponseModel;
import com.example.loanservice.datamapperlayer.customerdto.CustomerResponseModel;
import com.example.loanservice.datamapperlayer.employeedto.EmployeeResponseModel;
import com.example.loanservice.domainclientlayer.BookServiceClient;
import com.example.loanservice.domainclientlayer.CustomerServiceClient;
import com.example.loanservice.domainclientlayer.EmployeeServiceClient;
import com.example.loanservice.utils.exceptions.NotFoundException;
import com.example.loanservice.presentationlayer.LoanRequestModel;
import com.example.loanservice.presentationlayer.LoanResponseModel;
import com.example.loanservice.utils.exceptions.OverdueLoanModificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanResponseModelMapper loanResponseModelMapper;
    private final BookServiceClient bookServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final LoanRequestMapper loanRequestModelMapper;

    @Override
    public LoanResponseModel getLoanByLoanId(String loanId) {
        Loan loan = loanRepository.findByLoanIdentifier_LoanId(loanId)
                .orElseThrow(() -> new NotFoundException("Loan with ID " + loanId + " not found"));

        BookResponseModel book = bookServiceClient.getBookByBookId(loan.getBookIdentifier().getBookId());
        CustomerResponseModel customer = customerServiceClient.getCustomerByCustomerId(loan.getCustomerIdentifier().getCustomerId());
        EmployeeResponseModel employee = employeeServiceClient.getEmployeeByEmployeeId(loan.getEmployeeIdentifier().getEmployeeId());

        LoanResponseModel baseModel = loanResponseModelMapper.entityToResponseModel(loan);

        return baseModel.toBuilder()
                .bookTitle(book.getTitle())
                .bookAuthor(book.getAuthor())
                .customerFullName(customer.getFirstName() + " " + customer.getLastName())
                .employeeFullName(employee.getFirstName() + " " + employee.getLastName())
                .build();
    }

    @Override
    public List<LoanResponseModel> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        System.out.println("NB DE LOANS DANS MONGO : " + loans.size());

        return loans.stream()
                .map(this::enrichLoan)
                .collect(Collectors.toList());
    }

    @Override
    public LoanResponseModel addLoan(LoanRequestModel requestModel) {
        Loan loan = loanRequestModelMapper.requestModelToEntity(requestModel);
        loan.setLoanIdentifier(new LoanIdentifier()); // Génère un ID public
        Loan savedLoan = loanRepository.save(loan);
        return enrichLoan(savedLoan);
    }

    @Override
    public LoanResponseModel updateLoan(String loanId, LoanRequestModel requestModel) {
        Loan existingLoan = loanRepository.findByLoanIdentifier_LoanId(loanId)
                .orElseThrow(() -> new NotFoundException("Loan with ID " + loanId + " not found"));

        if (existingLoan.getReturnDate().isBefore(LocalDate.now())) {
            throw new OverdueLoanModificationException("Cannot update an overdue loan.");
        }

        loanRequestModelMapper.updateEntityFromModel(requestModel, existingLoan);
        Loan updatedLoan = loanRepository.save(existingLoan);
        return enrichLoan(updatedLoan);
    }

    @Override
    public void deleteLoan(String loanId) {
        Loan loan = loanRepository.findByLoanIdentifier_LoanId(loanId)
                .orElseThrow(() -> new NotFoundException("Loan with ID " + loanId + " not found"));

        if (loan.getReturnDate().isBefore(LocalDate.now())) {
            throw new OverdueLoanModificationException("Cannot delete an overdue loan.");
        }

        loanRepository.delete(loan);
    }

    private LoanResponseModel enrichLoan(Loan loan) {
        BookResponseModel book = bookServiceClient.getBookByBookId(loan.getBookIdentifier().getBookId());
        CustomerResponseModel customer = customerServiceClient.getCustomerByCustomerId(loan.getCustomerIdentifier().getCustomerId());
        EmployeeResponseModel employee = employeeServiceClient.getEmployeeByEmployeeId(loan.getEmployeeIdentifier().getEmployeeId());

        LoanResponseModel baseModel = loanResponseModelMapper.entityToResponseModel(loan);

        return baseModel.toBuilder()
                .bookTitle(book.getTitle())
                .bookAuthor(book.getAuthor())
                .customerFullName(customer.getFirstName() + " " + customer.getLastName())
                .employeeFullName(employee.getFirstName() + " " + employee.getLastName())
                .build();
    }
}
