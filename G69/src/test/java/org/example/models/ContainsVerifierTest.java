package org.example.models;

import org.example.Errors.UnsupportedOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContainsVerifierTest {
    ContainsVerifier verifier;
    @BeforeEach
    void setUp() {
        this.verifier = new ContainsVerifier();
    }

    @Test
    void containsSuccess() throws UnsupportedOperation {
        assertTrue(verifier.verify("hey there, I am here", "there"));
    }

    @Test
    void containsFail() throws UnsupportedOperation {
        assertFalse(verifier.verify("hey there, I am here", "hello"));
    }

    @Test
    void OnSameDateUnsupported(){
        Exception exception = assertThrows(UnsupportedOperation.class, ()->{
            verifier.verify(LocalDateTime.parse("2012-12-03T13:00"), LocalDateTime.parse("2012-12-03T12:00"));
        });
    }
}