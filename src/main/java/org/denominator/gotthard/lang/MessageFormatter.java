package org.denominator.gotthard.lang;

import java.util.Arrays;

public final class MessageFormatter {
    private MessageFormatter() {
        throw new AssertionError();
    }

    public static String format(String formatString, Object... arguments) {
        final StringBuilder sb = new StringBuilder(2 * formatString.length());

        char[] chars = formatString.toCharArray();
        int currentObject = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '{' && (i < chars.length - 1) && chars[i + 1] == '}') {
                i++;
                if (currentObject >= arguments.length) {
                    throw new IllegalArgumentException("Not enough arguments to construct a message: " + Arrays.toString(arguments));
                }
                sb.append(arguments[currentObject++]);
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    public static void out(String formatString, Object... arguments) {
        System.out.println(format(formatString, arguments));
    }
}
