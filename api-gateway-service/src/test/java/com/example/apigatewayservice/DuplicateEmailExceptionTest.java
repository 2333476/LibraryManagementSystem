package com.example.apigatewayservice;

import com.example.apigatewayservice.utils.exceptions.DuplicateEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateEmailExceptionTest {
    @Test
    void testDefaultConstructor() {
        DuplicateEmailException ex = new DuplicateEmailException();
        assertNotNull(ex);
    }

    @Test
    void testConstructorWithMessage() {
        DuplicateEmailException ex = new DuplicateEmailException("Email already exists");
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Conflict");
        DuplicateEmailException ex = new DuplicateEmailException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Conflict");
        DuplicateEmailException ex = new DuplicateEmailException("Email already exists", cause);
        assertEquals("Email already exists", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
