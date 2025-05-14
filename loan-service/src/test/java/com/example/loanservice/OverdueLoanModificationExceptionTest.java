package com.example.loanservice;

import com.example.loanservice.utils.exceptions.OverdueLoanModificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OverdueLoanModificationExceptionTest {

    @Test
    void testDefaultConstructor() {
        OverdueLoanModificationException ex = new OverdueLoanModificationException();
        assertNotNull(ex);
    }

    @Test
    void testMessageConstructor() {
        OverdueLoanModificationException ex = new OverdueLoanModificationException("Loan is overdue");
        assertEquals("Loan is overdue", ex.getMessage());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException("Cause");
        OverdueLoanModificationException ex = new OverdueLoanModificationException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("Cause");
        OverdueLoanModificationException ex = new OverdueLoanModificationException("Loan is overdue", cause);
        assertEquals("Loan is overdue", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
