package com.example.loanservice;
import com.example.loanservice.utils.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceNotFoundExceptionTest {
    @Test
    void testConstructor() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertEquals("Not found", ex.getMessage());
    }

    @Test
    void testSaleNotFound() {
        UUID id = UUID.randomUUID();
        ResourceNotFoundException ex = ResourceNotFoundException.saleNotFound(id);
        assertTrue(ex.getMessage().contains(id.toString()));
    }
}
