package org.example.models;

public class EqualsVerifier implements Verifier {
    @Override
    public boolean verify(Object x, Object y){
        return x.equals(y);
    }
}
