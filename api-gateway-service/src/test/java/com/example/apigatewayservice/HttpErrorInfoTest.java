package com.example.apigatewayservice;

import com.example.apigatewayservice.utils.HttpErrorInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

public class HttpErrorInfoTest {
    @Test
    void shouldStoreAndReturnHttpStatusPathAndMessage() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = "/api/test";
        String message = "Invalid request";

        HttpErrorInfo errorInfo = new HttpErrorInfo(status, path, message);

        assertEquals(status, errorInfo.getHttpStatus());
        assertEquals(path, errorInfo.getPath());
        assertEquals(message, errorInfo.getMessage());
    }
}
