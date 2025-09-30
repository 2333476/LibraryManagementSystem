package com.example.apigatewayservice;

import com.example.apigatewayservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotFoundExceptionTest {
    @Test
    void testDefaultConstructor() {
        NotFoundException ex = new NotFoundException();
        assertNotNull(ex);
    }

    @Test
    void testConstructorWithMessage() {
        NotFoundException ex = new NotFoundException("Test message");
        assertEquals("Test message", ex.getMessage());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Cause");
        NotFoundException ex = new NotFoundException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Cause");
        NotFoundException ex = new NotFoundException("Test message", cause);
        assertEquals("Test message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
