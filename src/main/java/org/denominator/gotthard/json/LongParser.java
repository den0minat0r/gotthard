package org.denominator.gotthard.json;

final class LongParser {
    private static final int MAX_LONG_LENGTH = String.valueOf(Long.MAX_VALUE).length();
    private static final long MULTIPLIERS[] = new long[MAX_LONG_LENGTH];

    private static final int MAX_HEX_LONG_LENGTH = Long.toHexString(Long.MAX_VALUE).length();
    private static final long HEX_MULTIPLIERS[] = new long[MAX_HEX_LONG_LENGTH];

    static {
        MULTIPLIERS[0] = 1;
        for (int i = 1; i < MULTIPLIERS.length; i++) {
            MULTIPLIERS[i] = MULTIPLIERS[i - 1] * 10;
        }

        HEX_MULTIPLIERS[0] = 1;
        for (int i = 1; i < HEX_MULTIPLIERS.length; i++) {
            HEX_MULTIPLIERS[i] = HEX_MULTIPLIERS[i - 1] * 16;
        }
    }

    static long parseLong(char[] aBuf, int offset, int length) {
        return parseLong(aBuf, offset, length, false);
    }

    static long parseHexLong(char[] aBuf, int offset, int length) {
        return parseLong(aBuf, offset, length, true);
    }

    private LongParser() {
        throw new AssertionError();
    }

    private static long parseLong(char[] aBuf, int offset, int length, boolean isHex) {
        boolean negative = false;

        long multipliers[] = (isHex ? HEX_MULTIPLIERS : MULTIPLIERS);

        if (length == 0) {
            throw new JsonParserException("Cannot parse an empty string.");
        }

        if (aBuf[offset] == '-') {
            negative = true;
        }

        final int startIndex = (negative ? offset + 1 : offset);

        if (negative & length == 1) {
            throw new JsonParserException("Incorrectly formed long value.");
        }

        long digit = 0;
        long result = 0;
        boolean illegalCharacter = false;
        final int endIndex = offset + length - 1;

        for (int i = endIndex; i >= startIndex; i--) {
            switch (aBuf[i]) {
                case '0':
                    continue;
                case '1':
                    digit = 1;
                    break;
                case '2':
                    digit = 2;
                    break;
                case '3':
                    digit = 3;
                    break;
                case '4':
                    digit = 4;
                    break;
                case '5':
                    digit = 5;
                    break;
                case '6':
                    digit = 6;
                    break;
                case '7':
                    digit = 7;
                    break;
                case '8':
                    digit = 8;
                    break;
                case '9':
                    digit = 9;
                    break;
                case 'A':
                case 'a':
                    if (!isHex) {
                        illegalCharacter = true;
                    } else {
                        digit = 10;
                    }
                    break;
                case 'B':
                case 'b':
                    if (!isHex) {
                        illegalCharacter = true;
                    } else {
                        digit = 11;
                    }
                    break;
                case 'C':
                case 'c':
                    if (!isHex) {
                        illegalCharacter = true;
                    } else {
                        digit = 12;
                    }
                    break;
                case 'D':
                case 'd':
                    if (!isHex) {
                        illegalCharacter = true;
                    } else {
                        digit = 13;
                    }
                    break;
                case 'E':
                case 'e':
                    if (!isHex) {
                        illegalCharacter = true;
                    } else {
                        digit = 14;
                    }
                    break;
                case 'F':
                case 'f':
                    if (!isHex) {
                        illegalCharacter = true;
                    } else {
                        digit = 15;
                    }
                    break;
                default:
                    illegalCharacter = true;
                    break;
            }

            if (illegalCharacter) {
                throw new JsonParserException("Illegal Character in Long: " + aBuf[i]);
            }

            if (endIndex - i >= multipliers.length) {
                throw new JsonParserException("String representation is too long to parse into Java long.");
            }

            result += digit * multipliers[endIndex - i];
        }

        if (negative) {
            result = -result;
        }
        return result;
    }
}
