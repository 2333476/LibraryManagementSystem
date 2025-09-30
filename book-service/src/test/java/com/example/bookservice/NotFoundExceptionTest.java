package com.example.bookservice;

import com.example.bookservice.utils.exceptions.NotFoundException;
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
        Throwable cause = new RuntimeException("Database error");
        NotFoundException ex = new NotFoundException("Not found", cause);
        assertEquals("Not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Only cause");
        NotFoundException ex = new NotFoundException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testDefaultConstructor() {
        NotFoundException ex = new NotFoundException();
        assertNull(ex.getMessage());
    }
}
