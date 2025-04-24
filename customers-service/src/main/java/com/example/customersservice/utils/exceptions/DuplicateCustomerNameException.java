package com.example.customersservice.utils.exceptions;

public class DuplicateCustomerNameException extends RuntimeException {

    public DuplicateCustomerNameException() {}

    public DuplicateCustomerNameException(String message) {
        super(message);
    }

    public DuplicateCustomerNameException(Throwable cause) {
        super(cause);
    }

    public DuplicateCustomerNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
