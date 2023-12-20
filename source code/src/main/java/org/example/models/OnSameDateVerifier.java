package org.example.models;

import org.example.Errors.UnsupportedOperation;

import java.time.LocalDateTime;

public class OnSameDateVerifier implements Verifier{
    @Override
    public boolean verify(Object x, Object y) throws UnsupportedOperation {
        if(x instanceof LocalDateTime && y instanceof LocalDateTime){
            return ((LocalDateTime) x).getYear() == ((LocalDateTime) y).getYear() &&
                    ((LocalDateTime) x).getMonthValue() == ((LocalDateTime) y).getMonthValue() &&
                    ((LocalDateTime) x).getDayOfMonth() == ((LocalDateTime) y).getDayOfMonth();
        }
        // TODO: implement exception handling
        throw new UnsupportedOperation("ON operation is not supported between " + x.getClass() + " and " + y.getClass());
    }
}
