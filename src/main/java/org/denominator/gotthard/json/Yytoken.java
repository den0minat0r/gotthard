package org.denominator.gotthard.json;

final class Yytoken {
    static final int TYPE_VALUE = 0;
    static final int TYPE_LEFT_BRACE = 1;
    static final int TYPE_RIGHT_BRACE = 2;
    static final int TYPE_LEFT_SQUARE = 3;
    static final int TYPE_RIGHT_SQUARE = 4;
    static final int TYPE_COMMA = 5;
    static final int TYPE_COLON = 6;
    static final int TYPE_EOF = -1;

    final int type;
    final Object value;
    private final String character;

    static final Yytoken LeftBrace = new Yytoken(TYPE_LEFT_BRACE, null, "{");
    static final Yytoken RightBrace = new Yytoken(TYPE_RIGHT_BRACE, null, "}");
    static final Yytoken LeftSquare = new Yytoken(TYPE_LEFT_SQUARE, null, "[");
    static final Yytoken RightSquare = new Yytoken(TYPE_RIGHT_SQUARE, null, "]");
    static final Yytoken Colon = new Yytoken(TYPE_COLON, null, ":");
    static final Yytoken Comma = new Yytoken(TYPE_COMMA, null, ",");
    static final Yytoken Eof = new Yytoken(TYPE_EOF, null, "EOF");

    private Yytoken(int aType, Object aValue, String aCharacter) {
        this.type = aType;
        this.value = aValue;
        this.character = aCharacter;
    }

    Yytoken(Object aValue) {
        this(TYPE_VALUE, aValue, "Value");
    }

    public String toString() {
        return "*" + character + "*,value: " + value;
    }
}
