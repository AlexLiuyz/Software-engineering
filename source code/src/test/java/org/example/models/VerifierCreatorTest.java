package org.example.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifierCreatorTest {

    @Test
    void createContains() {
        Verifier verifier = VerifierCreator.createVerifier(Operator.CONTAINS);
        assertEquals(ContainsVerifier.class, verifier.getClass());
    }

    @Test
    void createEquals()  {
        Verifier verifier = VerifierCreator.createVerifier(Operator.EQUALS);
        assertEquals(EqualsVerifier.class, verifier.getClass());
    }

    @Test
    void createAfter()  {
        Verifier verifier = VerifierCreator.createVerifier(Operator.AFTER);
        assertEquals(AfterVerifier.class, verifier.getClass());
    }

    @Test
    void createBefore()   {
        Verifier verifier = VerifierCreator.createVerifier(Operator.BEFORE);
        assertEquals(BeforeVerifier.class, verifier.getClass());
    }

    @Test
    void createOnSameDate()  {
        Verifier verifier = VerifierCreator.createVerifier(Operator.ONSAMEDATE);
        assertEquals(OnSameDateVerifier.class, verifier.getClass());
    }

}