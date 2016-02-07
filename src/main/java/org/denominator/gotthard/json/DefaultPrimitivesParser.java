package org.denominator.gotthard.json;

import java.math.BigDecimal;

final class DefaultPrimitivesParser implements JsonPrimitivesParser {
    @Override
    public Number parseInteger(char[] buffer, int offset, int length) {
        return LongParser.parseLong(buffer, offset, length);
    }

    @Override
    public Number parseFloating(char[] buffer, int offset, int length) {
        return new BigDecimal(buffer, offset, length).doubleValue();
    }
}
