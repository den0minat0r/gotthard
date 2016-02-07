package org.denominator.gotthard.lang;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public final class Strings {
    private Strings() {
        throw new AssertionError();
    }

    public static String join(Iterable<String> strings, String delimeter) {
        final StringBuilder sb = new StringBuilder();
        String ch = "";
        for (String s : strings) {
            sb.append(ch).append(s);
            ch = delimeter;
        }
        return sb.toString();
    }

    public static String join(String[] strings, String delimeter) {
        return join(Arrays.asList(strings), delimeter);
    }

    public static List<String> splitToList(String string, char splitChar) {
        final char[] chars = string.toCharArray();
        final List<String> res = new ArrayList<>();

        final StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == splitChar) {
                res.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(aChar);
            }
        }

        res.add(sb.toString());
        return res;
    }

    public static String[] split(String string, char splitChar) {
        final List<String> res = splitToList(string, splitChar);
        return res.toArray(new String[res.size()]);
    }

    public static String formatStackTrace(Exception e) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(1000);
        e.printStackTrace(new PrintStream(bytes));
        return e.getClass().getName() + "\n" + e.getMessage() + "\n" + bytes.toString();
    }

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Taken from http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
     *
     * @param str1
     * @param str2
     * @return
     */
    public static int computeLevenshteinDistance(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int[] d1 = new int[s2.length + 1];
        int[] d2 = new int[s2.length + 1];

        for (int j = 1; j <= s2.length; j++) {
            d1[j] = j;
        }

        for (int i = 1; i <= s1.length; i++) {
            d2[0] = i;
            for (int j = 1; j <= s2.length; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    d2[j] = minimum(d1[j] + 1, d2[j - 1] + 1, d1[j - 1]);
                } else {
                    d2[j] = minimum(d1[j] + 1, d2[j - 1] + 1, d1[j - 1] + 1);
                }
            }
            int[] swap = d1;
            d1 = d2;
            d2 = swap;
        }

        return d1[str2.length()];
    }

    public static Map<String, String> splitMap(String s, char pairSplitter, char valueSplitter) {
        final Map<String, String> map = new LinkedHashMap<>();
        if (s != null && !s.isEmpty()) {
            for (String pair : split(s, pairSplitter)) {
                final String[] split = split(pair, valueSplitter);
                Assertions.isTrue(split.length == 2, "Expected only two split results {}", pair);
                map.put(split[0], split[1]);
            }
        }
        return map;
    }
}
