package org.example.models;

import org.example.Errors.UnsupportedOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EqualsVerifierTest {

    EqualsVerifier verifier;
    @BeforeEach
    void setUp() {
        this.verifier = new EqualsVerifier();
    }

    @Test
    void equalString() throws UnsupportedOperation {
        assertTrue(verifier.verify("hey there", "hey there"));
        assertFalse(verifier.verify("hey there", "hey theres"));
    }

    @Test
    void equalLocalDateTime(){
        assertTrue(verifier.verify(LocalDateTime.parse("2012-12-03T12:00"), LocalDateTime.parse("2012-12-03T12:00")));
        assertFalse(verifier.verify(LocalDateTime.parse("2012-12-03T13:00"), LocalDateTime.parse("2012-12-03T12:00")));
    }

    @Test
    void containsFail() throws UnsupportedOperation {
        assertFalse(verifier.verify("hey there, I am here", "hello"));
    }


}