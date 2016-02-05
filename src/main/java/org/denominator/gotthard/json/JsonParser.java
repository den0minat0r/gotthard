package org.denominator.gotthard.json;

public interface JsonParser {
    <T> T parse(char[] buffer) throws JsonParserException;

    <T> T parse(String string) throws JsonParserException;
}

