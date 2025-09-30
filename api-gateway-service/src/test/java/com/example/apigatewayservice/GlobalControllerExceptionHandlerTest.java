package com.example.apigatewayservice;

import com.example.apigatewayservice.utils.GlobalControllerExceptionHandler;
import com.example.apigatewayservice.utils.HttpErrorInfo;
import com.example.apigatewayservice.utils.exceptions.DuplicateEmailException;
import com.example.apigatewayservice.utils.exceptions.InvalidInputException;
import com.example.apigatewayservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class GlobalControllerExceptionHandlerTest {
    private GlobalControllerExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalControllerExceptionHandler();
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("/test");
    }

    @Test
    void testHandleNotFoundException() {
        NotFoundException ex = new NotFoundException("Not found error");

        HttpErrorInfo response = handler.handleNotFoundException(webRequest, ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
        assertEquals("/test", response.getPath());
        assertEquals("Not found error", response.getMessage());
    }

    @Test
    void testHandleInvalidInputException() {
        InvalidInputException ex = new InvalidInputException("Invalid input");

        HttpErrorInfo response = handler.handleInvalidInputException(webRequest, ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getHttpStatus());
        assertEquals("/test", response.getPath());
        assertEquals("Invalid input", response.getMessage());
    }

    @Test
    void testHandleDuplicateVinException() {
        DuplicateEmailException ex = new DuplicateEmailException("Duplicate VIN");

        HttpErrorInfo response = handler.handleDuplicateVinException(webRequest, ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getHttpStatus());
        assertEquals("/test", response.getPath());
        assertEquals("Duplicate VIN", response.getMessage());
    }

    @Test
    void testCreateHttpErrorInfo() {
        Exception ex = new RuntimeException("Generic error");

        HttpErrorInfo info = GlobalControllerExceptionHandler.createHttpErrorInfo(
                HttpStatus.BAD_REQUEST, webRequest, ex);

        assertEquals(HttpStatus.BAD_REQUEST, info.getHttpStatus());
        assertEquals("/test", info.getPath());
        assertEquals("Generic error", info.getMessage());
    }
}
