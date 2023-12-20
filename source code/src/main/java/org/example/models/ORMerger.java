package org.example.models;

import java.util.LinkedList;
import java.util.List;

public class ORMerger implements Merger{
    @Override
    public List merge(List list1, List list2){
        List results= new LinkedList<>(list1);
        for(Object o: list2){
            if(!results.contains(o)){
                results.add(o);
            }
        }
        return results;
    }
}
