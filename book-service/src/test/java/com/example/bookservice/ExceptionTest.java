package com.example.bookservice.utils.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTest {

    @Test
    void testDuplicateISBNException_constructors() {
        DuplicateISBNException ex1 = new DuplicateISBNException();
        DuplicateISBNException ex2 = new DuplicateISBNException("message");
        DuplicateISBNException ex3 = new DuplicateISBNException(new Throwable("cause"));
        DuplicateISBNException ex4 = new DuplicateISBNException("message", new Throwable("cause"));

        assertNotNull(ex1);
        assertEquals("message", ex2.getMessage());
        assertEquals("cause", ex3.getCause().getMessage());
        assertEquals("message", ex4.getMessage());
    }

    @Test
    void testNotFoundException_constructors() {
        NotFoundException ex1 = new NotFoundException("Not found");
        assertEquals("Not found", ex1.getMessage());
    }

    @Test
    void testInvalidInputException_constructors() {
        InvalidInputException ex1 = new InvalidInputException("Invalid input");
        assertEquals("Invalid input", ex1.getMessage());
    }
}
