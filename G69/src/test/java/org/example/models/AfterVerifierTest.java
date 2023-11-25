package org.example.models;

import org.example.Errors.UnsupportedOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AfterVerifierTest {
    AfterVerifier verifier;
    @BeforeEach
    void setUp() {
        this.verifier = new AfterVerifier();
    }
    @Test
    void afterSuccess() throws UnsupportedOperation {
        assertTrue(verifier.verify(LocalDateTime.parse("2012-12-03T13:00"), LocalDateTime.parse("2012-12-03T12:00")));
    }

    @Test
    void afterFail() throws UnsupportedOperation {
        assertFalse(verifier.verify(LocalDateTime.parse("2012-12-03T11:00"), LocalDateTime.parse("2012-12-03T12:00")));
    }

    @Test
    void afterUnsupported(){
        Exception exception = assertThrows(UnsupportedOperation.class, ()->{
            verifier.verify("2012-12-03T13:00", "2012-12-03T12:00");
        });
    }
}