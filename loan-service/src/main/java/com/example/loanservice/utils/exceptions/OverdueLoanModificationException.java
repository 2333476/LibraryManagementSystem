package com.example.loanservice.utils.exceptions;

public class OverdueLoanModificationException extends RuntimeException{

    public OverdueLoanModificationException() {}

    public OverdueLoanModificationException(String message) { super(message); }

    public OverdueLoanModificationException(Throwable cause) { super(cause); }

    public OverdueLoanModificationException(String message, Throwable cause) { super(message, cause); }
}
