package org.example.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotFoundExceptionTest {
    @Test
    public void test_createInstanceWithMessageParameter() {
        String message = "Test message";
        NotFoundException exception = new NotFoundException(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void test_createInstanceWithNullMessageParameter() {
        NotFoundException exception = new NotFoundException(null);
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

}