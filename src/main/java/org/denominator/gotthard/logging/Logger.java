package org.denominator.gotthard.logging;

/**
 * Light interface to abstract from JDK logging and introduce late
 * string serialization
 */
public interface Logger {
    void trace(String message, Object... messageParams);

    void info(String message, Object... messageParams);

    void warning(String message, Object... messageParams);

    void error(Throwable t, String message, Object... messageParams);
}
