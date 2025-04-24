package com.example.bookservice.utils.exceptions;

public class DuplicateISBNException extends RuntimeException{

    public DuplicateISBNException() {}

    public DuplicateISBNException(String message) { super(message); }

    public DuplicateISBNException(Throwable cause) { super(cause); }

    public DuplicateISBNException(String message, Throwable cause) { super(message, cause); }
}
