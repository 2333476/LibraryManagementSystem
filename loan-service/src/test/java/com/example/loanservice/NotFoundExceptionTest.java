package com.example.loanservice;

import com.example.loanservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotFoundExceptionTest {

    @Test
    void constructorWithMessage_shouldStoreMessage() {
        NotFoundException ex = new NotFoundException("Resource not found");
        assertThat(ex.getMessage()).isEqualTo("Resource not found");
    }

    @Test
    void constructorWithCause_shouldStoreCause() {
        Throwable cause = new RuntimeException("Missing entity");
        NotFoundException ex = new NotFoundException(cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void constructorWithMessageAndCause_shouldStoreBoth() {
        Throwable cause = new RuntimeException("Entity error");
        NotFoundException ex = new NotFoundException("Error", cause);
        assertThat(ex.getMessage()).isEqualTo("Error");
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void noArgsConstructor_shouldCreateInstance() {
        NotFoundException ex = new NotFoundException();
        assertThat(ex).isInstanceOf(NotFoundException.class);
    }
}
