package com.example.customersservice;

import com.example.customersservice.utils.ResourceNotFoundException;
import com.example.customersservice.utils.exceptions.DuplicateCustomerNameException;
import com.example.customersservice.utils.exceptions.InvalidInputException;
import com.example.customersservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    // ✅ NotFoundException
    @Test
    void notFoundException_defaultConstructor() {
        NotFoundException exception = new NotFoundException();
        assertNotNull(exception);
    }

    @Test
    void notFoundException_withMessage() {
        NotFoundException exception = new NotFoundException("Not found");
        assertEquals("Not found", exception.getMessage());
    }

    @Test
    void notFoundException_withThrowable() {
        Throwable cause = new RuntimeException("Cause");
        NotFoundException exception = new NotFoundException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    void notFoundException_withMessageAndCause() {
        Throwable cause = new RuntimeException("Cause");
        NotFoundException exception = new NotFoundException("Not found", cause);
        assertEquals("Not found", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    // ✅ DuplicateCustomerNameException
    @Test
    void duplicateCustomerNameException_defaultConstructor() {
        DuplicateCustomerNameException exception = new DuplicateCustomerNameException();
        assertNotNull(exception);
    }

    @Test
    void duplicateCustomerNameException_withMessage() {
        DuplicateCustomerNameException exception = new DuplicateCustomerNameException("Duplicate name");
        assertEquals("Duplicate name", exception.getMessage());
    }

    @Test
    void duplicateCustomerNameException_withThrowable() {
        Throwable cause = new RuntimeException("Conflict");
        DuplicateCustomerNameException exception = new DuplicateCustomerNameException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    void duplicateCustomerNameException_withMessageAndCause() {
        Throwable cause = new RuntimeException("Conflict");
        DuplicateCustomerNameException exception = new DuplicateCustomerNameException("Duplicate name", cause);
        assertEquals("Duplicate name", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    // ✅ InvalidInputException
    @Test
    void invalidInputException_defaultConstructor() {
        InvalidInputException exception = new InvalidInputException();
        assertNotNull(exception);
    }

    @Test
    void invalidInputException_withMessage() {
        InvalidInputException exception = new InvalidInputException("Invalid input");
        assertEquals("Invalid input", exception.getMessage());
    }

    @Test
    void invalidInputException_withThrowable() {
        Throwable cause = new RuntimeException("Invalid field");
        InvalidInputException exception = new InvalidInputException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    void invalidInputException_withMessageAndCause() {
        Throwable cause = new RuntimeException("Invalid field");
        InvalidInputException exception = new InvalidInputException("Invalid input", cause);
        assertEquals("Invalid input", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    // ✅ ResourceNotFoundException
    @Test
    void test_saleNotFound() {
        UUID id = UUID.randomUUID();
        ResourceNotFoundException ex = ResourceNotFoundException.saleNotFound(id);
        assertTrue(ex.getMessage().contains(id.toString()));
    }

    @Test
    void test_ResourceNotFoundException_constructor() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Item not found");
        assertEquals("Item not found", ex.getMessage());
    }
}
