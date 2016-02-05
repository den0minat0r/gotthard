package org.denominator.gotthard.json;

import java.math.BigDecimal;

final class DefaultPrimitivesParser implements JsonPrimitivesParser {
    private final LongParser longParser = new LongParser();

    @Override
    public Number parseInteger(char[] aBuf, int offset, int length) {
        return longParser.parseLong(aBuf, offset, length);
    }

    @Override
    public Number parseFloating(char[] aBuf, int offset, int length) {
        return new BigDecimal(aBuf, offset, length).doubleValue();
    }
}
