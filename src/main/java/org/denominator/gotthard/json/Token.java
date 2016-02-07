package org.denominator.gotthard.json;

final class Token {
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

    static final Token LeftBrace = new Token(TYPE_LEFT_BRACE, null, "{");
    static final Token RightBrace = new Token(TYPE_RIGHT_BRACE, null, "}");
    static final Token LeftSquare = new Token(TYPE_LEFT_SQUARE, null, "[");
    static final Token RightSquare = new Token(TYPE_RIGHT_SQUARE, null, "]");
    static final Token Colon = new Token(TYPE_COLON, null, ":");
    static final Token Comma = new Token(TYPE_COMMA, null, ",");
    static final Token Eof = new Token(TYPE_EOF, null, "EOF");

    private Token(int type, Object value, String character) {
        this.type = type;
        this.value = value;
        this.character = character;
    }

    Token(Object value) {
        this(TYPE_VALUE, value, "Value");
    }

    public String toString() {
        return "*" + character + "*,value: " + value;
    }
}
