package org.denominator.gotthard.lang;

import org.denominator.gotthard.collection.Creator;
import org.denominator.gotthard.junit.LangAssert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {
    @Test
    public void utilityConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(Strings.class);
    }

    @Test
    public void join() {
        assertEquals("test,", Strings.join(new String[]{"test", ""}, ","));
        assertEquals("", Strings.join(new String[]{}, ","));
    }

    @Test
    public void split() {
        assertArrayEquals(new String[]{"test"}, Strings.split("test", ','));
        assertArrayEquals(new String[]{"test", ""}, Strings.split("test,", ','));
        assertArrayEquals(new String[]{"test", ";"}, Strings.split("test,;", ','));

        assertArrayEquals(new String[]{"", ""}, Strings.split(",", ','));
        assertArrayEquals(new String[]{"", "", ""}, Strings.split(",,", ','));
        assertArrayEquals(new String[]{""}, Strings.split("", ','));

    }

    @Test
    public void formatStack() {
        assertTrue(Strings.formatStackTrace(new RuntimeException()).startsWith("java.lang.RuntimeException"));
    }

    @Test
    public void levenshtein() {
        assertEquals(0, Strings.computeLevenshteinDistance("1", "1"));
        assertEquals(1, Strings.computeLevenshteinDistance("1", "2"));
        assertEquals(1, Strings.computeLevenshteinDistance("13", "123"));
        assertEquals(2, Strings.computeLevenshteinDistance("13", "1231"));
        assertEquals(4, Strings.computeLevenshteinDistance("asdc", "1231"));
    }

    @Test
    public void splitMap() {
        assertEquals(Creator.linkedMap(), Strings.splitMap("", ',', '='));
        assertEquals(Creator.linkedMap(), Strings.splitMap(null, ',', '='));
        assertEquals(Creator.linkedMap("a", "1", "b", "2"), Strings.splitMap("a=1,b=2", ',', '='));
        assertEquals(Creator.linkedMap("a=1", "b=2"), Strings.splitMap("a=1,b=2", '&', ','));
    }
}
