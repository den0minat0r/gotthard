package org.denominator.gotthard.json;

public interface JsonPrimitivesParser {
    /**
     * Invoked by the parser when integer number has to be produced.
     *
     * @param aBuf   buffer with JSON string
     * @param offset first character in the array to inspect
     * @param length number of characters to consider
     * @return parsed integer number
     */
    Number parseInteger(char[] aBuf, int offset, int length);

    /**
     * Invoked by the parser when floating point number has to be produced.
     *
     * @param aBuf   buffer with JSON string
     * @param offset first character in the array to inspect
     * @param length number of characters to consider
     * @return parsed floating point number
     */
    Number parseFloating(char[] aBuf, int offset, int length);
}
