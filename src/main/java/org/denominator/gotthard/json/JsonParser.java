package org.denominator.gotthard.json;

public interface JsonParser {
    /***
     * @param buffer string that contains serialized json
     * @param <T> allows to specify any type the user expects
     * @return parsed object
     * @throws JsonParserException if parsing error occurs
     */
    <T> T parse(char[] buffer) throws JsonParserException;

    /***
     * Same as above
     * @param string serialized json
     * @param <T> return type
     * @return parsed object
     * @throws JsonParserException if parsing error occurs
     */
    <T> T parse(String string) throws JsonParserException;
}

