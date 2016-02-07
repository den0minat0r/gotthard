package org.denominator.gotthard.junit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class LangAssert {
    private LangAssert() {
        throw new AssertionError();
    }

    public static void assertUtilityClass(Class<?> clazz) {
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            Assert.assertTrue("Constructor is expected to be private.", !constructor.isAccessible());
            constructor.setAccessible(true);
            try {
                constructor.newInstance();
                throw new AssertionError("Constructor was expected to throw AssertionError");
            } catch (InvocationTargetException e) {
                if (!(e.getCause() instanceof AssertionError)) {
                    throw new AssertionError("Expected AssertionError, received " + e.getCause());
                }
            } finally {
                constructor.setAccessible(false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
