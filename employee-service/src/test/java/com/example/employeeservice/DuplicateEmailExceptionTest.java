package com.example.employeeservice;

import com.example.employeeservice.utils.exceptions.DuplicateEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateEmailExceptionTest {
    @Test
    void testConstructorWithMessage() {
        DuplicateEmailException ex = new DuplicateEmailException("Duplicate email");
        assertEquals("Duplicate email", ex.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Cause");
        DuplicateEmailException ex = new DuplicateEmailException("Duplicate email", cause);
        assertEquals("Duplicate email", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Only cause");
        DuplicateEmailException ex = new DuplicateEmailException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testDefaultConstructor() {
        DuplicateEmailException ex = new DuplicateEmailException();
        assertNull(ex.getMessage());
    }
}
