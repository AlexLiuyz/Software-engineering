package org.example.models;

import org.example.Errors.UnsupportedOperation;

public class ContainsVerifier implements Verifier {
    @Override
    public boolean verify(Object given, Object target) throws UnsupportedOperation {
        if(given instanceof String && target instanceof String){
            return ((String) given).contains((String)target);
        }
        throw new UnsupportedOperation("CONTAINS operation is not supported between " + given.getClass() + " and " + target.getClass());
    }
}
