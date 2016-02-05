package org.denominator.gotthard.json;

import java.util.*;


final class DefaultJsonParser implements JsonParser {
    private static final String INVALID_TOKEN = "Invalid Token %s at position %d.";

    private static final int S_INIT              = 0;
    private static final int S_IN_FINISHED_VALUE = 1;
    private static final int S_IN_OBJECT         = 2;
    private static final int S_IN_ARRAY          = 3;
    private static final int S_PASSED_PAIR_KEY   = 4;
    private static final int S_IN_ERROR          = -1;

    private final JsonObjectFactory   jsonObjectFactory;
    private final JsonPrimitivesParser primitivesParser;

    public DefaultJsonParser(JsonObjectFactory jsonObjectFactory, JsonPrimitivesParser jsonPrimitivesParser) {
        this.jsonObjectFactory = jsonObjectFactory;
        if (jsonObjectFactory == null){
            throw new IllegalArgumentException("jsonObjectFactory cannot be null.");
        }
        this.primitivesParser = jsonPrimitivesParser;
        if (jsonPrimitivesParser == null){
            throw new IllegalArgumentException("jsonObjectFactory cannot be null.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T parse(String string) throws JsonParserException {
        return parse(string.toCharArray());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T parse(char[] buffer) throws JsonParserException {
        Object parsed = parseArray(buffer);
        return (T) parsed;
    }

    private Object parseArray(char[] buffer) throws JsonParserException {
        if (buffer.length == 0) {
            return null;
        }

        final Yylex lexer = new Yylex(buffer, primitivesParser);

        int status = S_INIT;
        Yytoken token;

        final Deque<Integer> statusStack = new LinkedList<>();
        final Deque<Object> valueStack = new LinkedList<>();

        do {
            token = lexer.yylex();

            switch (status) {
                case S_INIT:
                    switch (token.type) {
                        case Yytoken.TYPE_VALUE:
                            status = S_IN_FINISHED_VALUE;
                            statusStack.addFirst(status);
                            valueStack.addFirst(token.value);
                            break;
                        case Yytoken.TYPE_LEFT_BRACE:
                            status = S_IN_OBJECT;
                            statusStack.addFirst(status);
                            valueStack.addFirst(jsonObjectFactory.mapInstance());
                            break;
                        case Yytoken.TYPE_LEFT_SQUARE:
                            status = S_IN_ARRAY;
                            statusStack.addFirst(status);
                            valueStack.addFirst(jsonObjectFactory.listInstance());
                            break;
                        default:
                            status = S_IN_ERROR;
                    }
                    break;

                case S_IN_FINISHED_VALUE:
                    if (token.type == Yytoken.TYPE_EOF) {
                        return valueStack.removeFirst();
                    } else {
                        throw new JsonParserException(String.format(INVALID_TOKEN, String.valueOf(token), lexer.getPosition()));
                    }

                case S_IN_OBJECT:
                    switch (token.type) {
                        case Yytoken.TYPE_COMMA:
                            break;
                        case Yytoken.TYPE_VALUE:
                            if (token.value instanceof String) {
                                String key = (String) token.value;
                                valueStack.addFirst(key);
                                status = S_PASSED_PAIR_KEY;
                                statusStack.addFirst(status);
                            } else {
                                status = S_IN_ERROR;
                            }
                            break;
                        case Yytoken.TYPE_RIGHT_BRACE:
                            if (valueStack.size() > 1) {
                                statusStack.removeFirst();
                                valueStack.removeFirst();
                                status = statusStack.getFirst();
                            } else {
                                status = S_IN_FINISHED_VALUE;
                            }
                            break;
                        default:
                            status = S_IN_ERROR;
                            break;
                    }//inner switch
                    break;

                case S_PASSED_PAIR_KEY:
                    switch (token.type) {
                        case Yytoken.TYPE_COLON:
                            break;
                        case Yytoken.TYPE_VALUE:
                            statusStack.removeFirst();
                            String key = (String) valueStack.removeFirst();
                            Map<String, Object> parent = toMap(valueStack.getFirst());
                            parent.put(key, token.value);
                            status = statusStack.getFirst();
                            break;
                        case Yytoken.TYPE_LEFT_SQUARE:
                            statusStack.removeFirst();
                            key = (String) valueStack.removeFirst();
                            parent = toMap(valueStack.getFirst());
                            List<Object> newArray = jsonObjectFactory.listInstance();
                            parent.put(key, newArray);
                            status = S_IN_ARRAY;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newArray);
                            break;
                        case Yytoken.TYPE_LEFT_BRACE:
                            statusStack.removeFirst();
                            key = (String) valueStack.removeFirst();
                            parent = toMap(valueStack.getFirst());
                            Map<String, Object> newObject = jsonObjectFactory.mapInstance();
                            parent.put(key, newObject);
                            status = S_IN_OBJECT;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newObject);
                            break;
                        default:
                            status = S_IN_ERROR;
                    }
                    break;

                case S_IN_ARRAY:
                    switch (token.type) {
                        case Yytoken.TYPE_COMMA:
                            break;
                        case Yytoken.TYPE_VALUE:
                            List<Object> val = toList(valueStack.getFirst());
                            val.add(token.value);
                            break;
                        case Yytoken.TYPE_RIGHT_SQUARE:
                            if (valueStack.size() > 1) {
                                statusStack.removeFirst();
                                valueStack.removeFirst();
                                status = statusStack.getFirst();
                            } else {
                                status = S_IN_FINISHED_VALUE;
                            }
                            break;
                        case Yytoken.TYPE_LEFT_BRACE:
                            val = toList(valueStack.getFirst());
                            Map<String, Object> newObject = jsonObjectFactory.mapInstance();
                            val.add(newObject);
                            status = S_IN_OBJECT;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newObject);
                            break;
                        case Yytoken.TYPE_LEFT_SQUARE:
                            val = toList(valueStack.getFirst());
                            List<Object> newArray = jsonObjectFactory.listInstance();
                            val.add(newArray);
                            status = S_IN_ARRAY;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newArray);
                            break;
                        default:
                            status = S_IN_ERROR;
                    }
                    break;
            }
        } while (token.type != Yytoken.TYPE_EOF && status != S_IN_ERROR);

        throw new JsonParserException(String.format(INVALID_TOKEN, String.valueOf(token), lexer.getPosition()));
    }

    private static Map<String, Object> toMap(Object object) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) object;
        return map;
    }

    private static List<Object> toList(Object object) {
        @SuppressWarnings("unchecked")
        final List<Object> list = (List<Object>) object;
        return list;
    }
}
