package com.example.bookservice;

import com.example.bookservice.utils.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
public class ResourceNotFoundExceptionTest {
    @Test
    void testConstructorWithMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Missing resource");
        assertEquals("Missing resource", ex.getMessage());
    }

    @Test
    void testSaleNotFoundStaticMethod() {
        UUID id = UUID.randomUUID();
        ResourceNotFoundException ex = ResourceNotFoundException.saleNotFound(id);

        assertTrue(ex.getMessage().contains(id.toString()));
    }
}
