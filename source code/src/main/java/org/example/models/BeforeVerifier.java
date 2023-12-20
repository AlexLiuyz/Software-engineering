package org.example.models;

import org.example.Errors.UnsupportedOperation;

import java.time.LocalDateTime;

public class BeforeVerifier implements Verifier{
    @Override
    public boolean verify(Object x, Object y) throws UnsupportedOperation {
        if(x instanceof LocalDateTime && y instanceof LocalDateTime){
            return ((LocalDateTime) x).isBefore((LocalDateTime) y);
        }
        // TODO: implement exception handling
        throw new UnsupportedOperation("BEFORE operation is not supported between " + x.getClass() + " and " + y.getClass());

    }
}
