package com.example.employeeservice;

import com.example.employeeservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class NotFoundExceptionTest {
    @Test
    void testConstructorWithMessage() {
        NotFoundException ex = new NotFoundException("Not found");
        assertEquals("Not found", ex.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Missing item");
        NotFoundException ex = new NotFoundException("Not found", cause);
        assertEquals("Not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Cause only");
        NotFoundException ex = new NotFoundException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testDefaultConstructor() {
        NotFoundException ex = new NotFoundException();
        assertNull(ex.getMessage());
    }
}
