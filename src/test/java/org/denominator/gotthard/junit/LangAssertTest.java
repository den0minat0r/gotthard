package org.denominator.gotthard.junit;

import org.junit.Test;

public class LangAssertTest {
    public static final class PublicConstructor {
        public PublicConstructor() {

        }
    }

    public static final class WrongException {
        private WrongException() {
            throw new IllegalStateException();
        }
    }

    @Test(expected = AssertionError.class)
    public void publicConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(PublicConstructor.class);
    }

    @Test(expected = AssertionError.class)
    public void wrongException() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(WrongException.class);
    }

    @Test
    public void utilityConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(LangAssert.class);
    }

    @Test(expected = AssertionError.class)
    public void notUtilityClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(String.class);
    }

    @Test
    public void assertDoubles() {
        ArrayAssert.assertDoubles(Double.MAX_VALUE, Double.MAX_VALUE);
        ArrayAssert.assertDoubles(Double.MIN_VALUE, Double.MIN_VALUE);
    }

    @Test
    public void assertArrayDouble() {
        ArrayAssert.assertDoubleArrays(new double[]{Double.MAX_VALUE}, new double[]{Double.MAX_VALUE});
    }
}
