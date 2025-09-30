package com.example.employeeservice;

import com.example.employeeservice.utils.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvalidInputExceptionTest {
    @Test
    void testConstructorWithMessage() {
        InvalidInputException ex = new InvalidInputException("Invalid input");
        assertEquals("Invalid input", ex.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Cause");
        InvalidInputException ex = new InvalidInputException("Invalid input", cause);
        assertEquals("Invalid input", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Cause only");
        InvalidInputException ex = new InvalidInputException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testDefaultConstructor() {
        InvalidInputException ex = new InvalidInputException();
        assertNull(ex.getMessage());
    }
}
