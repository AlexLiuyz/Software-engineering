package org.example.models;

import org.example.Errors.UnsupportedOperation;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class AfterVerifier implements Verifier{
    @Override
    public boolean verify(Object x, Object y) throws UnsupportedOperation {
        if(x instanceof LocalDateTime && y instanceof LocalDateTime){
            return ((LocalDateTime) x).isAfter((LocalDateTime) y);
        }
        // TODO: implement exception handling
        throw new UnsupportedOperation("AFTER operation is not supported between " + x.getClass() + " and " + y.getClass());
    }
}
