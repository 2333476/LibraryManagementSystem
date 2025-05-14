package com.example.loanservice;

import com.example.loanservice.utils.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidInputExceptionTest {

    @Test
    void constructorWithMessage_shouldStoreMessage() {
        InvalidInputException ex = new InvalidInputException("Invalid input provided");
        assertThat(ex.getMessage()).isEqualTo("Invalid input provided");
    }

    @Test
    void constructorWithCause_shouldStoreCause() {
        Throwable cause = new RuntimeException("Root cause");
        InvalidInputException ex = new InvalidInputException(cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void constructorWithMessageAndCause_shouldStoreBoth() {
        Throwable cause = new RuntimeException("Root cause");
        InvalidInputException ex = new InvalidInputException("Invalid", cause);
        assertThat(ex.getMessage()).isEqualTo("Invalid");
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void noArgsConstructor_shouldCreateException() {
        InvalidInputException ex = new InvalidInputException();
        assertThat(ex).isInstanceOf(InvalidInputException.class);
    }
}
