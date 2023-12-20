package org.example.models;

public abstract class Entity{
    String id;
    public abstract void setId(String id);

    public abstract String getId();

    public abstract String toString();

    public abstract Entity getCopy();

}
