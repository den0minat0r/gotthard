package org.denominator.gotthard.lang;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public final class Assertions {
    private Assertions() {
        throw new AssertionError();
    }

    public static final String UNREACHABLE_CODE_LINE_ERROR = "This code line should not be reached.";

    public static void fail() {
        throw new IllegalArgumentException(UNREACHABLE_CODE_LINE_ERROR);
    }

    public static void notNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException("Field " + name + " cannot be null.");
        }
    }

    public static void notNull(Map<String, Object> objects) {
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            notNull(entry.getValue(), entry.getKey());
        }
    }

    public static void isTrue(Supplier<Boolean> condition, String message, Object... messageArguments) {
        isTrue(condition.get(), message, messageArguments);
    }

    public static void isTrue(boolean condition, String message, Object... messageArguments) {
        if (!condition) {
            throw new IllegalArgumentException(MessageFormatter.format(message, messageArguments));
        }
    }

    public static void notEmpty(String string, String name) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("String parameter " + name + " cannot be empty.");
        }
    }

    public static void notEmpty(Collection<?> collection, String name) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection " + name + " cannot be empty.");
        }
    }

    public static void notEmpty(Map<?, ?> map, String name) {
        notNull(map, name);
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Collection " + name + " cannot be empty.");
        }
    }

    public static void containsOnly(String string, char[] allowedCharacters) {
        final char[] array = string.toCharArray();
        for (char ch : array) {
            boolean found = false;
            for (char allowedCharacter : allowedCharacters) {
                if (ch == allowedCharacter) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("String " + string
                        + " contains illegal character '" + ch + "', allowed: '"
                        + Arrays.toString(allowedCharacters) + "'");
            }
        }
    }

    public static void isFile(Path path) {
        isExists(path);
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException(path.toAbsolutePath() + " is not a file.");
        }
    }

    public static void isFile(File file) {
        isFile(file.toPath());
    }

    public static void isDirectory(Path path) {
        isExists(path);
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException(path.toAbsolutePath() + " is not a file.");
        }
    }

    private static void isExists(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(path.toAbsolutePath() + " does not exist.");
        }
    }

    public static void isDirectory(File file) {
        isDirectory(file.toPath());
    }

    public static void isPositive(double number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException("Parameter " + name + " must be positive");
        }
    }

    public static void nonZero(double number, String name) {
        if (number == 0) {
            throw new IllegalArgumentException("Parameter " + name + " cannot be 0.");
        }
    }

    public static void notNan(double value, String message, Object... messageArguments) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(MessageFormatter.format(message, messageArguments));
        }
    }
}
