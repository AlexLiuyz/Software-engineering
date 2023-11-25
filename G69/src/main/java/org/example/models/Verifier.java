package org.example.models;

import org.example.Errors.UnsupportedOperation;

public interface Verifier {
    public boolean verify(Object a, Object b) throws UnsupportedOperation;
}
