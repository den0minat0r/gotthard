package org.denominator.gotthard.collection;

import org.denominator.gotthard.junit.LangAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CreatorTest {
    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(Creator.class);
    }

    @Test
    public void linkedMaps () {
        assertEquals(Creator.list(5,4,3,2,1),new ArrayList<>(Creator.linkedMap(5,1,4,1,3,1,2,1,1,1).keySet()));
    }

    @Test
    public void sortedMaps () {
        assertEquals(Creator.list(1,2,3,4,5),new ArrayList<>(Creator.sortedMap(5,1,4,1,3,1,2,1,1,1).keySet()));
    }

    @Test
    public void testMaps() {
        assertEquals(Creator.map("1", 1, "2", 2, "3", 3, "4", 4, "5", 5)
                , Arrays.asList(1,2,3,4,5).stream().collect(Collectors.toMap(Object::toString, x->x)));
    }

    @Test
    public void testSets() {
        assertEquals(Creator.set("1"), Creator.set("1"));
        assertEquals(Creator.sortedSet("1", "2"), new TreeSet<>(Arrays.asList("2", "1")));
        assertEquals(Creator.list("b", "c", "g", "3")
                , new ArrayList<>(Creator.linkedSet("b", "c", "g", "3")));
    }
}
