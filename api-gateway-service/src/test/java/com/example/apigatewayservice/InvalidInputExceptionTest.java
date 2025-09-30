package com.example.apigatewayservice;


import com.example.apigatewayservice.utils.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvalidInputExceptionTest {
    @Test
    void testDefaultConstructor() {
        InvalidInputException ex = new InvalidInputException();
        assertNotNull(ex);
    }

    @Test
    void testConstructorWithMessage() {
        InvalidInputException ex = new InvalidInputException("Invalid input provided");
        assertEquals("Invalid input provided", ex.getMessage());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Root cause");
        InvalidInputException ex = new InvalidInputException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        InvalidInputException ex = new InvalidInputException("Invalid input provided", cause);
        assertEquals("Invalid input provided", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
