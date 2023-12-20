package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergerCreatorTest {

    @Test
    void andMerger(){
        Merger merger = MergerCreator.createMerger(Connector.AND);
        assertEquals(ANDMerger.class, merger.getClass());
    }

    @Test
    void ORMerger(){
        Merger merger = MergerCreator.createMerger(Connector.OR);
        assertEquals(ORMerger.class, merger.getClass());
    }
}