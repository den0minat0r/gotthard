package org.denominator.gotthard.logging;

/***
 * Entry point to logging
 */
public final class LoggerFactory {
    private LoggerFactory() {
        throw new AssertionError();
    }

    public static Logger getLogger(Class<?> clazz) {
        return new JdkLogger(clazz);
    }
}
