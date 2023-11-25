package org.example.models;

public class Filter {
    private String attribute;
    private Object target;
    private Operator operator;


    public Filter(String attribute, Object target, Operator operator) {
        this.attribute = attribute;
        this.target = target;
        this.operator = operator;
    }

    public Filter(String attribute, Operator operator) {
        this.attribute = attribute;
        this.operator = operator;
    }


    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
