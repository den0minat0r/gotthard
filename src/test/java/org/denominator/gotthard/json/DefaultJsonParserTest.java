package org.denominator.gotthard.json;

import org.denominator.gotthard.collection.Creator;
import org.denominator.junit.LangAssert;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class DefaultJsonParserTest {
    private JsonParser jsonParser = null;

    @Before
    public void initParser() {
        jsonParser = Json.defaultParser();
    }

    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(Json.class);
    }

    @Test
    public void defaultParser(){
        assertEquals(Creator.map("a", false), Json.parse("{\"a\" : false}"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void noObjectFactory() {
        new DefaultJsonParser(null, new DefaultPrimitivesParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPrimitiveParser() {
        new DefaultJsonParser(new DefaultJsonObjectFactory(), null);
    }

    @Test
    public void empty() {
        Assert.assertNull(jsonParser.parse(""));
    }

    @Test
    public void scalarLong() {
        assertEquals((Long) 1L, jsonParser.parse("1"));
    }

    @Test
    public void scalarNull() {
        assertEquals((Object) null, jsonParser.parse("null"));
    }

    @Test
    public void scalarString() {
        assertEquals("abc", jsonParser.parse("\"abc\""));
    }

    @Test
    public void scalarBoolean() {
        assertEquals(true, jsonParser.parse("true"));
        assertEquals(false, jsonParser.parse("false"));
    }

    @Test
    public void scalarDouble() {
        String[] doubleTests = new String[]{"1.23", "01.23", "1.23e00"
                , "12.3e-01", "0.123e+01", "1.23E00"
                , "12.3E-01", "0.123E+01", "-1.23", "-01.23", "-1.23e00"
                , "-12.3e-01", "-0.123e+01", "-1.23E00"
                , "-12.3E-01", "-0.123E+01"
        };

        for (String number : doubleTests) {
            assertEquals(new Double(number), jsonParser.parse(number));
        }
    }

    @Test
    public void emptyList() {
        assertEquals(Collections.emptyList(), jsonParser.parse("[]"));
    }

    @Test
    public void emptyMap() {
        assertEquals(Collections.emptyMap(), jsonParser.parse("{}"));
    }

    @Test
    public void listParse() {
        List<Long> list = jsonParser.parse("[1,2]");

        assertEquals(Arrays.asList(1L, 2L), list);
    }

    @Test
    public void mapParse() {
        Map<String, Object> map = jsonParser.parse("{\"1\":1,\"2\":\"abc\"}");

        assertEquals(Creator.map("1", 1L, "2", "abc"), map);
    }

    @Test
    public void nestedListInMap() {
        Map<String, Object> map = jsonParser.parse("{\"1\":[1,2],\"2\":\"abc\"}");

        assertEquals(Creator.map("1", Arrays.asList(1L, 2L), "2", "abc"), map);
    }

    @Test
    public void nestedMapInList() {
        List<Object> list = jsonParser.parse("[{},{\"1\":true}]");

        assertEquals(Arrays.asList(new HashMap<>()
                , Creator.map("1", true)), list);
    }

    @Test
    public void nestedMapsInLists() {
        List<?> list = jsonParser.parse("[{\"a\":{}},[\"b\"]]");

        assertEquals(Arrays.asList(Creator.map("a", Creator.map())
                , Collections.singletonList("b")), list);
    }

    @Test
    public void specialCharacters() {
        String s = jsonParser.parse("\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"");

        assertEquals(s, "\"\\/\b\f\n\r\t");
    }

    @Test
    public void unicodeCharacters() {
        String s = jsonParser.parse("\"\\u0021\\u0023\"");

        assertEquals(s, "!#");
    }

    @Test
    public void wrongJson() {
        String wrongJson[] = new String[]{"'"
                , "'abc'", "1.2w", "[abc]", "{[],[']}", "{{},''}", "123a"
                , "{[:]}", "[:]", "[[[}]]]", "[{}}]", "{true}", "{\"a\"::}", "[],",
                "true]", "[{", "{[", "}]", "][}{", "}["};

        for (String s : wrongJson) {
            try {
                Object parsed = jsonParser.parse(s);
                Assert.fail("Expected parsing exception. Received: " + parsed);
            } catch (JsonParserException ignored) {

            }
        }
    }

    @Test
    public void wrongJson2() {
        String wrongJson[] = new String[]{"[1,\"2"};

        for (String s : wrongJson) {
            try {
                Object parsed = jsonParser.parse(s);
                Assert.fail("Expected parsing exception. Received: " + parsed);
            } catch (JsonParserException ignored) {

            }
        }
    }

    @Test
    public void longJson() {
        StringBuilder sb = new StringBuilder("[0,");
        final int size = 100000;
        for (int i = 1; i < size; i++) {
            sb.append(",").append(i);
        }
        sb.append(']');
        List<Long> list = jsonParser.parse(sb.toString());

        assertEquals(size, list.size());
        for (int i = 0; i < size; i++) {
            assertEquals(i, list.get(i).intValue());
        }
    }

    @Test
    public void jsonWithSpaces() {
        assertEquals((Long) 1L, jsonParser.parse("\n\n 1 "));

        List<String> list = jsonParser.parse("\n \n\r \r\n \r \t [\"1\\n\",\t \"\\t2\\t\" \n]\r\n \t");

        assertEquals(Arrays.asList("1\n", "\t2\t"), list);
    }

    @Test
    public void testObjectFactory() {
        JsonObjectFactory factory = EasyMock.createStrictMock(JsonObjectFactory.class);
        JsonParser parser = new DefaultJsonParser(factory, new DefaultPrimitivesParser());

        List<Object> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        EasyMock.expect(factory.listInstance()).andReturn(list);
        EasyMock.expect(factory.mapInstance()).andReturn(map);
        EasyMock.replay(factory);

        Assert.assertTrue(parser.parse("[]") == list);
        Assert.assertTrue(parser.parse("{}") == map);

        EasyMock.verify(factory);
    }

    @Test
    public void testCustomParsing() {
        JsonPrimitivesParser primitivesParser = EasyMock.createStrictMock(JsonPrimitivesParser.class);
        JsonParser parser = new DefaultJsonParser(new DefaultJsonObjectFactory(), primitivesParser);

        char[] floatingChars = "1.2".toCharArray();
        char[] integerChars = "3".toCharArray();

        final Number floatingNumber = new BigDecimal("1.2");
        final Short integerNumber = 3;

        EasyMock.expect(primitivesParser.parseFloating(floatingChars, 0, 3)).andReturn(floatingNumber);
        EasyMock.expect(primitivesParser.parseInteger(integerChars, 0, 1)).andReturn(integerNumber);
        EasyMock.replay(primitivesParser);

        Object returnedFloating = parser.parse(floatingChars);
        Assert.assertTrue(returnedFloating == floatingNumber);

        Object returnedInteger = parser.parse(integerChars);
        Assert.assertTrue(returnedInteger == integerNumber);

        EasyMock.verify(primitivesParser);
    }
}
