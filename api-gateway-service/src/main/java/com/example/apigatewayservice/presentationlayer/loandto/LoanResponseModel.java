package com.example.apigatewayservice.presentationlayer.loandto;

import java.time.LocalDate;

public class LoanResponseModel {

    private String loanId;
    private String bookId;
    private String customerId;
    private String employeeId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean isOverdue;
    private String bookTitle;
    private String bookAuthor;
    private String customerFullName;
    private String employeeFullName;

    public LoanResponseModel() {}

    public LoanResponseModel(String loanId, String bookId, String customerId, String employeeId, LocalDate loanDate, LocalDate returnDate, boolean isOverdue, String bookTitle, String bookAuthor, String customerFullName, String employeeFullName) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.isOverdue = isOverdue;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.customerFullName = customerFullName;
        this.employeeFullName = employeeFullName;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }
}
