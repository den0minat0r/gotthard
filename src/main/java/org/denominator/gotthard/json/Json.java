package org.denominator.gotthard.json;

public final class Json {
    private static final JsonObjectFactory DEFAULT_FACTORY = new DefaultJsonObjectFactory();
    private static final JsonPrimitivesParser DEFAULT_PRIMITIVES_PARSER = new DefaultPrimitivesParser();
    private static final JsonParser DEFAULT_PARSER = new DefaultJsonParser(DEFAULT_FACTORY, DEFAULT_PRIMITIVES_PARSER);

    private Format               format               = Format.Compact;
    private JsonObjectFactory    jsonObjectFactory    = DEFAULT_FACTORY;
    private JsonPrimitivesParser jsonPrimitivesParser = DEFAULT_PRIMITIVES_PARSER;

    public Json format(Format format) {
        this.format = format;
        return this;
    }

    public Json jsonObjectFactory(JsonObjectFactory jsonObjectFactory) {
        this.jsonObjectFactory = jsonObjectFactory;
        return this;
    }

    public Json jsonPrimitivesParser(JsonPrimitivesParser jsonPrimitivesParser) {
        this.jsonPrimitivesParser = jsonPrimitivesParser;
        return this;
    }

    public JsonParser newParser() {
        return new DefaultJsonParser(jsonObjectFactory, jsonPrimitivesParser);
    }

    public String serialize(Object object) {
        return StaticJsonSerializer.serializeJson(object, this.format);
    }

    public static Object parse(String s) {
        return DEFAULT_PARSER.parse(s);
    }

    public static String serialize (Object object, Format format) {
        return StaticJsonSerializer.serializeJson(object, format);
    }
}
