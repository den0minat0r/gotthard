package org.denominator.gotthard.json;

import java.util.List;
import java.util.Map;

//This class does not have any state, therefore a singleton is acceptable
final class StaticJsonSerializer {
    private static final String LINE_SEPARATOR = "\r\n";
    private static final String ONE_TAB        = "    ";
    public static final  String NULL_OBJECT    = "null";

    private StaticJsonSerializer() {
        throw new AssertionError();
    }

    private enum SerializationType {
        String(String.class),
        Number(Number.class),
        Boolean(Boolean.class),
        Map(Map.class),
        List(List.class);

        private final Class<?> clazz;

        SerializationType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public static SerializationType fromClass(Class<?> clazz) {
            for (SerializationType type : SerializationType.values()) {
                if (type.clazz.isAssignableFrom(clazz)) {
                    return type;
                }
            }
            return null;
        }
    }

    static String serializeJson(Object object, Format format) {
        final StringBuilder builder = new StringBuilder();

        serializeObject(object, builder, format, 0);

        return builder.toString();
    }

    private static void serializeObject(Object object, StringBuilder sb, Format format, int level) {
        if (object == null) {
            sb.append(NULL_OBJECT);
            return;
        }

        final SerializationType type = SerializationType.fromClass(object.getClass());
        if (type == null) {
            serializeString(object.toString(), sb);
            return;
        }

        switch (type) {
            case String:
                serializeString((String) object, sb);
                break;
            case Map:
                @SuppressWarnings("unchecked")
                final Map<String, Object> mapObject = (Map<String, Object>) object;
                serializeMap(mapObject, sb, format, level + 1);
                break;
            case List:
                @SuppressWarnings("unchecked")
                final List<Object> listObject = (List<Object>) object;
                serializeList(listObject, sb, format, level + 1);
                break;
            case Number:
                sb.append(object.toString());
                break;
            case Boolean:
                sb.append(object.toString());
                break;
        }
    }

    private static void serializeMap(Map<String, Object> map, StringBuilder sb, Format format, int level) {
        sb.append("{");

        String separator = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(separator);

            insertHumanFormatting(sb, format, level);

            final String key = entry.getKey();
            if (key == null) {
                throw new IllegalArgumentException("Map keys must be non null Strings.");
            }
            serializeString(key, sb);
            sb.append(":");
            final Object value = entry.getValue();
            if (value instanceof List || value instanceof Map) {
                insertHumanFormatting(sb, format, level + 1);
            }
            serializeObject(value, sb, format, level + 1);

            separator = ",";
        }

        insertHumanFormatting(sb, format, level - 1);
        sb.append("}");
    }

    private static void serializeList(List<Object> aList, StringBuilder sb, Format format, int level) {
        sb.append("[");

        String separator = "";
        for (Object obj : aList) {
            sb.append(separator);

            insertHumanFormatting(sb, format, level);

            serializeObject(obj, sb, format, level);

            separator = ",";
        }

        insertHumanFormatting(sb, format, level - 1);
        sb.append("]");
    }

    private static void insertHumanFormatting(StringBuilder sb, Format format, int level) {
        if (Format.HumanReadable == format) {
            sb.append(LINE_SEPARATOR);
            insertTabLevel(sb, level);
        }
    }

    private static void insertTabLevel(StringBuilder stringBuilder, int level) {
        for (int i = 0; i < level; i++) {
            stringBuilder.append(ONE_TAB);
        }
    }

    private static void serializeString(String aString, StringBuilder sb) {
        sb.append("\"");
        for (int i = 0; i < aString.length(); i++) {
            char ch = aString.charAt(i);

            /* taken from json.org */
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (Character.isISOControl(ch)) {
                        unicodeEscape(ch, sb);
                    } else {
                        sb.append(ch);
                    }
                    break;
            }
        }
        sb.append("\"");
    }

    private final static char[] HEX = "0123456789ABCDEF".toCharArray();

    private static void unicodeEscape(char aCharacter, StringBuilder aBuilder) {
        aBuilder.append("\\u");
        int n = aCharacter;
        for (int i = 0; i < 4; ++i) {
            int digit = (n & 0xf000) >> 12;
            aBuilder.append(HEX[digit]);
            n <<= 4;
        }
    }
}

