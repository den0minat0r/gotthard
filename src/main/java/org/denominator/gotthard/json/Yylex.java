package org.denominator.gotthard.json;

final class Yylex {

    /**
     * This character denotes the end of file
     */
    public static final int YYEOF = -1;
    private static final LongParser longParser = new LongParser();

    private final JsonPrimitivesParser primitivesParser;
    /**
     * lexical states
     */
    public static final int YYINITIAL = 0;
    public static final int STRING_BEGIN = 2;

    private static final int ZZ_LEXSTATE[] = {0, 0, 1, 1};

    /**
     * Translates characters to character classes
     */
    private static final String ZZ_CMAP_PACKED =
            "\11\0\1\7\1\7\2\0\1\7\22\0\1\7\1\0\1\11\10\0" +
                    "\1\6\1\31\1\2\1\4\1\12\12\3\1\32\6\0\4\1\1\5" +
                    "\1\1\24\0\1\27\1\10\1\30\3\0\1\22\1\13\2\1\1\21" +
                    "\1\14\5\0\1\23\1\0\1\15\3\0\1\16\1\24\1\17\1\20" +
                    "\5\0\1\25\1\0\1\26\uff82\0";

    private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

    private static char[] zzUnpackCMap(CharSequence packed) {
        char[] map = new char[65536];
        int i = 0;  /* index in packed string  */
        int j = 0;  /* index in unpacked array */
        while (i < 90) {
            int count = packed.charAt(i++);
            char value = packed.charAt(i++);
            do {
                map[j++] = value;
            } while (--count > 0);
        }
        return map;
    }

    private static final String ZZ_ACTION_PACKED_0 =
            "\2\0\2\1\1\2\1\3\1\4\3\1\1\5\1\6" +
                    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\5\0" +
                    "\1\14\1\16\1\17\1\20\1\21\1\22\1\23\1\24" +
                    "\1\0\1\25\1\0\1\25\4\0\1\26\1\27\2\0" +
                    "\1\30";

    private static final int[] ZZ_ACTION = zzUnpack(ZZ_ACTION_PACKED_0);

    private static int[] zzUnpack(String packed) {
        int[] result = new int[45];
        int i = 0;       /* index in packed string  */
        int j = 0;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return result;
    }

    /**
     * Translates a state to a row index in the transition table
     */

    private static final String ZZ_ROWMAP_PACKED_0 =
            "\0\0\0\33\0\66\0\121\0\154\0\207\0\66\0\242" +
                    "\0\275\0\330\0\66\0\66\0\66\0\66\0\66\0\66" +
                    "\0\363\0\u010e\0\66\0\u0129\0\u0144\0\u015f\0\u017a\0\u0195" +
                    "\0\66\0\66\0\66\0\66\0\66\0\66\0\66\0\66" +
                    "\0\u01b0\0\u01cb\0\u01e6\0\u01e6\0\u0201\0\u021c\0\u0237\0\u0252" +
                    "\0\66\0\66\0\u026d\0\u0288\0\66";

    private static final int[] ZZ_ROWMAP = zzUnpack(ZZ_ROWMAP_PACKED_0);

    /**
     * The transition table of the DFA
     */
    private static final int ZZ_TRANS[] = {
            2, 2, 3, 4, 2, 2, 2, 5, 2, 6,
            2, 2, 7, 8, 2, 9, 2, 2, 2, 2,
            2, 10, 11, 12, 13, 14, 15, 16, 16, 16,
            16, 16, 16, 16, 16, 17, 18, 16, 16, 16,
            16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
            16, 16, 16, 16, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 4, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 4, 19, 20, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 20, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 5, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            21, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 22, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            23, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 16, 16, 16, 16, 16, 16, 16,
            16, -1, -1, 16, 16, 16, 16, 16, 16, 16,
            16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
            -1, -1, -1, -1, -1, -1, -1, -1, 24, 25,
            26, 27, 28, 29, 30, 31, 32, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            33, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, 34, 35, -1, -1,
            34, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            36, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 37, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 38, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 39, -1, 39, -1, 39, -1, -1,
            -1, -1, -1, 39, 39, -1, -1, -1, -1, 39,
            39, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 33, -1, 20, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, 20, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 35,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 38, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 40,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 41, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 42, -1, 42, -1, 42,
            -1, -1, -1, -1, -1, 42, 42, -1, -1, -1,
            -1, 42, 42, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 43, -1, 43, -1, 43, -1, -1, -1,
            -1, -1, 43, 43, -1, -1, -1, -1, 43, 43,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 44,
            -1, 44, -1, 44, -1, -1, -1, -1, -1, 44,
            44, -1, -1, -1, -1, 44, 44, -1, -1, -1,
            -1, -1, -1, -1, -1
    };

    private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

    private static final String ZZ_ATTRIBUTE_PACKED_0 =
            "\2\0\1\11\3\1\1\11\3\1\6\11\2\1\1\11" +
                    "\5\0\10\11\1\0\1\1\1\0\1\1\4\0\2\11" +
                    "\2\0\1\11";

    private static int[] zzUnpackAttribute() {
        int[] result = new int[45];
        int i = 0;       /* index in packed string  */
        int j = 0;  /* index in unpacked array */
        int l = ZZ_ATTRIBUTE_PACKED_0.length();
        while (i < l) {
            int count = ZZ_ATTRIBUTE_PACKED_0.charAt(i++);
            int value = ZZ_ATTRIBUTE_PACKED_0.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return result;
    }

    /**
     * the current lexical state
     */
    private int zzLexicalState = YYINITIAL;

    /**
     * this buffer contains the current text to be matched and is
     * the source of the yytext() string
     */
    private final char zzBuffer[];

    /**
     * the textposition at the last accepting state
     */
    private int zzMarkedPos;

    /**
     * startRead marks the beginning of the yytext() string in the buffer
     */
    private int zzStartRead;

    /**
     * the number of characters up to the start of the matched text
     */
    private int yychar;

    private final StringBuilder sb = new StringBuilder();


    Yylex(char[] aBuffer, JsonPrimitivesParser aParser) {
        zzBuffer = aBuffer;
        primitivesParser = aParser;
    }

    int getPosition() {
        return yychar;
    }

    @SuppressWarnings("fallthrough")
    Yytoken yylex() throws JsonParserException {
        int zzInput;
        int zzAction;

        while (true) {
            yychar += zzMarkedPos - zzStartRead;

            zzAction = -1;

            int zzCurrentPos = zzStartRead = zzMarkedPos;

            int zzState = ZZ_LEXSTATE[zzLexicalState];

            innerLoop:
            {
                while (true) {
                    if (zzCurrentPos < zzBuffer.length) {
                        zzInput = zzBuffer[zzCurrentPos++];
                    } else {
                        zzInput = YYEOF;
                        break innerLoop;
                    }

                    int zzNext = ZZ_TRANS[ZZ_ROWMAP[zzState] + ZZ_CMAP[zzInput]];
                    if (zzNext == -1) {
                        break innerLoop;
                    }

                    zzState = zzNext;

                    int zzAttributes = ZZ_ATTRIBUTE[zzState];
                    if ((zzAttributes & 1) == 1) {
                        zzAction = zzState;
                        zzMarkedPos = zzCurrentPos;
                        if ((zzAttributes & 8) == 8) {
                            break innerLoop;
                        }
                    }
                }
            }

            switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
                case 11: {
                    sb.append(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
                }
                case 25:
                    break;
                case 4: {
                    sb.delete(0, sb.length());
                    zzLexicalState = STRING_BEGIN;
                }
                case 26:
                    break;
                case 16: {
                    sb.append('\b');
                }
                case 27:
                    break;
                case 6: {
                    return Yytoken.RightBrace;
                }
                case 23: {
                    Boolean val = Boolean.valueOf(new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead));
                    return new Yytoken(val);
                }
                case 22: {
                    return new Yytoken(null);
                }
                case 13: {
                    zzLexicalState = YYINITIAL;
                    return new Yytoken(sb.toString());
                }
                case 12: {
                    sb.append('\\');
                }
                case 32:
                    break;
                case 21: {
                    return new Yytoken(primitivesParser.parseFloating(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead));
                }
                case 1: {
                    throw new JsonParserException(String.format("Unexpected character [%d,'%c'], last position %d."
                            , yychar, (char) yychar, zzMarkedPos));
                }
                case 8: {
                    return Yytoken.RightSquare;
                }
                case 19: {
                    sb.append('\r');
                }
                case 36:
                    break;
                case 15: {
                    sb.append('/');
                }
                case 37:
                    break;
                case 10: {
                    return Yytoken.Colon;
                }
                case 14: {
                    sb.append('"');
                }
                case 39:
                    break;
                case 5: {
                    return Yytoken.LeftBrace;
                }
                case 17: {
                    sb.append('\f');
                }
                case 41:
                    break;
                case 24: {
                    int ch = (int) longParser.parseHexLong(zzBuffer, zzStartRead + 2, zzMarkedPos - zzStartRead - 2);
                    sb.append((char) ch);
                }
                case 42:
                    break;
                case 20: {
                    sb.append('\t');
                }
                case 43:
                    break;
                case 7: {
                    return Yytoken.LeftSquare;
                }
                case 2: {
                    return new Yytoken(primitivesParser.parseInteger(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead));
                }
                case 18: {
                    sb.append('\n');
                }
                case 46:
                    break;
                case 9: {
                    return Yytoken.Comma;
                }
                default:
                    if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
                        return Yytoken.Eof;
                    }
            }
        }
    }
}
