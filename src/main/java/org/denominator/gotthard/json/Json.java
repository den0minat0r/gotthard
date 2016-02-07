package org.denominator.gotthard.json;

/***
 * Entry point for Json library
 */
public final class Json {
    private static final JsonObjectFactory DEFAULT_FACTORY = new DefaultJsonObjectFactory();
    private static final JsonPrimitivesParser DEFAULT_PRIMITIVES_PARSER = new DefaultPrimitivesParser();
    private static final JsonParser DEFAULT_PARSER = new DefaultJsonParser(DEFAULT_FACTORY, DEFAULT_PRIMITIVES_PARSER);

    private static final JsonObjectFactory ORDERED_FACTORY = new OrderedJsonObjectFactory();
    private static final JsonParser ORDERED_PARSER = new DefaultJsonParser(ORDERED_FACTORY, DEFAULT_PRIMITIVES_PARSER);

    private static final JsonObjectFactory SORTED_FACTORY = new SortedJsonObjectFactory();
    private static final JsonParser SORTED_PARSER = new DefaultJsonParser(SORTED_FACTORY, DEFAULT_PRIMITIVES_PARSER);

    private Json(){
        throw new AssertionError();
    }

    /**
     *
     * @return Default parser that does not guarantee the order of keys in maps, but
     * offers the best performance
     */
    public static JsonParser defaultParser() { return DEFAULT_PARSER; }

    /***
     * @return Parser that preserves the order of keys in maps
     */
    public static JsonParser orderedParser() { return ORDERED_PARSER; }

    /***
     * @return Parser that sorts the keys in maps
     */
    public static JsonParser sortedParser() { return SORTED_PARSER; }

    /***
     * @param s string to parse
     * @return parsed object using the default parser
     */
    public static Object parse(String s) {
        return DEFAULT_PARSER.parse(s);
    }

    /***
     * @param object Object to serialize, the objects that are not Map, List, Number, Boolean or Strings are
     *               serialized as with .toString() representation
     * @param format controls serialization formatting
     * @return serialized json
     */
    public static String serialize (Object object, Format format) {
        return StaticJsonSerializer.serializeJson(object, format);
    }
}
