package org.denominator.gotthard.json;

import org.denominator.gotthard.junit.LangAssert;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LongParserTest {

    private long parse(String s) {
        char[] buf = s.toCharArray();
        long res1 = LongParser.parseLong(buf, 0, buf.length);

        char[] buf1 = new char[buf.length + 1];
        Arrays.fill(buf1, 'x');
        System.arraycopy(buf, 0, buf1, 1, buf.length);
        long res2 = LongParser.parseLong(buf1, 1, buf.length);

        char[] buf2 = new char[buf.length + 1];
        Arrays.fill(buf2, 'x');
        System.arraycopy(buf, 0, buf2, 0, buf.length);
        long res3 = LongParser.parseLong(buf2, 0, buf.length);

        char[] buf3 = new char[buf.length + 2];
        Arrays.fill(buf3, 'x');
        System.arraycopy(buf, 0, buf3, 1, buf.length);
        long res4 = LongParser.parseLong(buf3, 1, buf.length);

        long res5 = Long.parseLong(s);

        Assert.assertEquals(res1, res2);
        Assert.assertEquals(res2, res3);
        Assert.assertEquals(res3, res4);
        Assert.assertEquals(res4, res5);

        return res1;
    }

    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(LongParser.class);
    }

    @Test
    public void zeroLong() {
        Assert.assertEquals(0L, parse("000"));
        Assert.assertEquals(0L, parse("0"));
        Assert.assertEquals(0L, parse("-0"));
        Assert.assertEquals(0L, parse("-000"));
    }

    @Test
    public void negativeLong() {
        Assert.assertEquals((-1234L), parse("-1234"));
    }

    @Test
    public void maxMinLong() {
        Assert.assertEquals(Long.MAX_VALUE, parse(String.valueOf(Long.MAX_VALUE)));
        Assert.assertEquals(Long.MIN_VALUE, parse(String.valueOf(Long.MIN_VALUE)));

        Assert.assertEquals(Long.MAX_VALUE, parseHex(Long.toHexString(Long.MAX_VALUE)));
        Assert.assertEquals(Long.MIN_VALUE, parseHex(Long.toHexString(Long.MIN_VALUE)));
    }

    @Test
    public void trailingZerosLong() {
        Assert.assertEquals(1L, parse("00001"));
        Assert.assertEquals(1L, parse("0000000000000000000000000000000000000000000000000000000000000000000001"));
        Assert.assertEquals(2001L, parse("02001"));

        Assert.assertEquals((-1L), parse("-00001"));
        Assert.assertEquals((-2001L), parse("-02001"));
    }

    @Test
    public void faultyLongs() {
        String[] faults = new String[]{"", "-", "12s12", "-123.", "000012-", "111111111111111111111111111111111111111"
                , "a", "A", "b", "B", "c", "C", "d", "D", "e", "E", "f", "F"};

        for (String s : faults) {
            try {
                long result = parse(s);
                Assert.fail("Expected exception. Received: " + result);
            } catch (JsonParserException ignored) {

            }
        }
    }

    @Test
    public void faultyHexLongs() {
        String[] faults = new String[]{"", "-", "1r", "123.", "000012-", "111111111111111111111111111111111111111"};

        for (String s : faults) {
            try {
                long result = parseHex(s);
                Assert.fail("Expected exception. Received: " + result);
            } catch (JsonParserException ignored) {

            }
        }
    }

    @Test
    public void hexParser() {
        String checks[] = new String[]{"aA", "aAbBcCdDeEfF", "-AabcC"};
        parseHex("aA");
        for (String check : checks) {
            Assert.assertEquals(Long.parseLong(check, 16), parseHex(check));
        }
    }

    private long parseHex(String s) {
        char[] buf = s.toCharArray();
        return LongParser.parseHexLong(buf, 0, buf.length);
    }
}
