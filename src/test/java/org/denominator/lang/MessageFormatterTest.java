package org.denominator.lang;

import org.denominator.gotthard.lang.MessageFormatter;
import org.denominator.junit.LangAssert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageFormatterTest {
    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(MessageFormatter.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEnoughArguments() {
        MessageFormatter.format("No arguments: {}");
    }

    @Test
    public void tooManyArguments() {
        assertEquals("test {", MessageFormatter.format("test {", 1, 2));
    }

    @Test
    public void incompleteFormatting() {
        assertEquals("test {", MessageFormatter.format("test {"));
    }

    @Test
    public void normalFormatting() {
        assertEquals("1: null, 2: test", MessageFormatter.format("1: {}, 2: {}", null, "test"));
    }

    @Test
    public void out() {
        MessageFormatter.out("Test format {}", 1);
    }
}
