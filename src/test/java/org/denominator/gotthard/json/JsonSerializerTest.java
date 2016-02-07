package org.denominator.gotthard.json;

import org.denominator.gotthard.collection.Creator;
import org.denominator.gotthard.junit.LangAssert;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonSerializerTest {

    @Test
    public void serializationFormat() {
        final Map map = Creator.map("1",1,"aaa",2,"bbb",4);
        String s = Json.serialize(map, Format.Compact);
        String s2 = Json.serialize(map, Format.HumanReadable);
        Assert.assertNotEquals(s, s2);
        Assert.assertEquals(s, s2.replace("\n","").replace("\t","").replace("\r","").replace(" ",""));
    }

    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(StaticJsonSerializer.class);
    }

    @Test(expected = ClassCastException.class)
    public void wrongKeyType() {
        Json.serialize(Creator.map(1L, ""), Format.Compact);
    }

    @Test
    public void nullObject() {
        performTwoWayTests(new Object[]{null});
    }

    @Test
    public void primitiveTypes() {
        performTwoWayTests(true, false, 1L, new Double("23.4"), "abc");
    }

    @Test
    public void list() {
        performTwoWayTests(Arrays.asList(1L, false, "abc"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullKey() {
        Map<Object, Object> map = new HashMap<>();
        map.put(null, "");
        Json.serialize(map, Format.Compact);
    }

    @Test(expected = ClassCastException.class)
    public void nonStringKey() {
        Map<Object, Object> map = new HashMap<>();
        map.put(Long.MAX_VALUE, "");
        Json.serialize(map, Format.Compact);
    }

    @Test
    public void map() {
        performTwoWayTests(Creator.map("1", 1L,"test", null));
    }

    @Test
    public void nested() {
        performTwoWayTests(Creator.map(
                "abc", Arrays.asList(true, false, new Double("0010.00023")
                , "test", null)));
    }

    @Test
    public void specialCharacters() {
        performTwoWayTests("\"", "\\", "\\/", "\b\f\n\r\t\t\t\t");
    }

    @Test
    public void isoCharacters() {
        StringBuilder sb = new StringBuilder();
        for (int ch = 0x0000; ch <= 0x001F; ch++) {
            sb.append((char) ch);
        }
        for (int ch = 0x007F; ch <= 0x009F; ch++) {
            sb.append((char) ch);
        }
        performTwoWayTests(sb.toString());
    }

    @Test
    public void notKnownObject() {
        String result = Json.serialize(new Dummy(), Format.Compact);
        Assert.assertEquals("\"dummy\"", result);
    }

    @Test
    public void showFormattedJson() {
        Map<String, Object> object = Creator.map("array"
                , Arrays.asList(1, Arrays.asList("string1", "string2", true, false, null)
                , Creator.map("abcdefg", Arrays.asList('a', 'b'))
                , 4)
                , "test", 4
                , "nested map", Creator.map("long name", "test", "another name", true));
        System.out.println(Json.serialize(object, Format.Compact));
        System.out.println(Json.serialize(object, Format.HumanReadable));
    }

    private void compareJsonParsing(Object obj, JsonParser parser, Format format) {
        String serialized = Json.serialize(obj, format);
        Object unparsed = null;
        try {
            unparsed = parser.parse(serialized);
        } catch (JsonParserException e) {
            Assert.fail("Failed to parse: " + serialized);
        }
        Assert.assertEquals("Failed to compare original and parsed verison, Original: " + obj
                + ", serialized: " + serialized
                + ", unparsed: " + unparsed, obj, unparsed);
    }

    private void performTwoWayTests(Object... objects) {
        for (Format format : Format.values()) {
            for (Object obj : objects) {
                compareJsonParsing(obj, Json.defaultParser(), format);
                compareJsonParsing(obj, Json.orderedParser(), format);
                compareJsonParsing(obj, Json.sortedParser(), format);
            }
        }
    }
}
