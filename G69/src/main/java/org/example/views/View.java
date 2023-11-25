package org.example.views;

import org.example.models.Entity;
import org.example.models.Filter;

import java.util.List;

public abstract class View<T extends Entity> {
    public abstract void displayList(List<T> items);
    public abstract void displayDetails(T item);
    public void displayError(Exception e){

    }
    public void displayFilters(List<Filter> filters){

    }

}
