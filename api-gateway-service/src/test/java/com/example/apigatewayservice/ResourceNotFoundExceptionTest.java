package com.example.apigatewayservice;

import com.example.apigatewayservice.utils.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceNotFoundExceptionTest {
    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateSaleNotFoundException() {
        UUID id = UUID.randomUUID();
        ResourceNotFoundException exception = ResourceNotFoundException.saleNotFound(id);

        assertTrue(exception.getMessage().contains(id.toString()));
    }

}
