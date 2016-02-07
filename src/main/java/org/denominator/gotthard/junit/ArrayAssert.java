package org.denominator.gotthard.junit;

import java.util.Arrays;


public final class ArrayAssert {
    private ArrayAssert() {
        throw new AssertionError();
    }

    private static String errorMessage(String expected, String actuals) {
        return "Arrays are not equal.\nExpected: " + expected + "\nActual: " + actuals + "\n";
    }

    public static void assertArrayEquals(String[] expected, String[] actuals) {
        boolean equal = Arrays.equals(expected, actuals);
        if (!equal) {
            Assert.assertTrue(errorMessage(Arrays.toString(expected), Arrays.toString(actuals)), equal);
        }
    }

    public static void assertMatrix(double[][] m1, double[][] m2) {
        Assert.assertEquals(m1.length, m2.length);

        for (int i = 0; i < m1.length; i++) {
            assertDoubleArrays(m1[i], m2[i]);
        }
    }

    public static void assertMatrix(String[][] m1, String[][] m2) {
        Assert.assertEquals(m1.length, m2.length);

        for (int i = 0; i < m1.length; i++) {
            assertArrayEquals(m1[i], m2[i]);
        }
    }

    public static void assertDoubles(double v1, double v2) {
        Assert.assertEquals(v1 + " != " + v2, Double.doubleToLongBits(v1), Double.doubleToLongBits(v2));
    }

    public static void assertDoubleArrays(double[] v1, double[] v2) {
        Assert.assertEquals("v1.length, v2.length", v1.length, v2.length);
        for (int i = 0; i < v1.length; i++) {
            assertDoubles(v1[i], v2[i]);
        }
    }
}
