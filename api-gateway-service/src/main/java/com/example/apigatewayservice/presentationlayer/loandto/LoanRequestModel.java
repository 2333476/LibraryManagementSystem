package com.example.apigatewayservice.presentationlayer.loandto;

import java.time.LocalDate;

public class LoanRequestModel {

    private String bookId;
    private String customerId;
    private String employeeId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public LoanRequestModel() {
    }

    public LoanRequestModel(String bookId, String customerId, String employeeId, LocalDate loanDate, LocalDate returnDate) {
        this.bookId = bookId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
