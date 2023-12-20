package org.example.models;

public class VerifierCreator {
    public static Verifier createVerifier(Operator op) {
        Verifier verifier;
        switch (op){
            case CONTAINS:
                verifier= new ContainsVerifier();
                break;
            case EQUALS:
                verifier= new EqualsVerifier();
                break;
            case AFTER:
                verifier= new AfterVerifier();
                break;
            case BEFORE:
                verifier= new BeforeVerifier();
                break;
            case ONSAMEDATE:
                verifier= new OnSameDateVerifier();
                break;
            default:
                verifier = new EqualsVerifier();
        }
        return verifier;
    }
}
