package org.example.models;

import java.util.LinkedList;
import java.util.List;

public class ANDMerger implements Merger {
    @Override
    public List merge(List list1, List list2){
        List results = new LinkedList<>();
        for( Object o: list1){
            if(list2.contains(o)){
                results.add(o);
            }
        }
        return results;
    }
}
