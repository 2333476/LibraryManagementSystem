package com.example.employeeservice;

import com.example.employeeservice.utils.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceNotFoundExceptionTest {
    @Test
    void testConstructorWithMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertEquals("Not found", ex.getMessage());
    }

    @Test
    void testSaleNotFoundStaticMethod() {
        UUID id = UUID.randomUUID();
        ResourceNotFoundException ex = ResourceNotFoundException.saleNotFound(id);
        assertTrue(ex.getMessage().contains(id.toString()));
    }
}
