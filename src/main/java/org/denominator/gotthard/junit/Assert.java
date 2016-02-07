package org.denominator.gotthard.junit;

import java.util.Objects;

final class Assert {
    private Assert() {
        throw new AssertionError();
    }

    static void assertEquals(Object o1, Object o2) {
        assertEquals("", o1, o2);
    }

    static void assertEquals(String message, Object o1, Object o2) {
        assertTrue(message, Objects.equals(o1, o2));
    }

    static void assertEquals(double o1, double o2, double epsilon) {
        if (Math.abs(o1 - o2) >= epsilon) {
            throw new AssertionError();
        }
    }

    static void assertTrue(boolean condition) {
        assertTrue("", condition);
    }

    static void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
