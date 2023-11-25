package org.example.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    @Test
    void contructor1(){
        String attribute = "name";
        Operator op = Operator.EQUALS;
        Filter filter = new Filter(attribute, op);

        assertEquals(attribute, filter.getAttribute());
        assertEquals(op, filter.getOperator());
    }

    @Test
    void testSetters(){
        String attribute = "name";
        String target = "target";
        Operator op = Operator.EQUALS;
        Filter filter = new Filter(attribute, target, op);

        assertEquals(attribute, filter.getAttribute());
        assertEquals(target, filter.getTarget());
        assertEquals(op, filter.getOperator());

        String newAttribute = "name";
        String newTarget = "target";
        Operator newOp = Operator.BEFORE;

        filter.setAttribute(newAttribute);
        filter.setTarget(newTarget);
        filter.setOperator(newOp);

        assertEquals(newAttribute, filter.getAttribute());
        assertEquals(newTarget, filter.getTarget());
        assertEquals(newOp, filter.getOperator());

    }

}