package org.example.models;

public class MergerCreator {
    public static Merger createMerger(Connector connector){
        Merger merger;
        switch (connector){
            case AND:
                merger = new ANDMerger();
                break;
            case OR:
                merger = new ORMerger();
                break;
            default:
                merger = new ANDMerger();
                break;
        }
        return merger;
    }
}
