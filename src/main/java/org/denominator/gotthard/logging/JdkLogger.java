package org.denominator.gotthard.logging;

import org.denominator.gotthard.lang.MessageFormatter;

import java.util.logging.Level;

final class JdkLogger implements Logger {
    private final java.util.logging.Logger logger;
    JdkLogger(Class<?> clazz) {
        this.logger = java.util.logging.Logger.getLogger(clazz.getName());
    }

    @Override
    public void trace(String message, Object... messageParams) {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, MessageFormatter.format(message, messageParams));
        }
    }

    @Override
    public void info(String message, Object... messageParams) {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, MessageFormatter.format(message, messageParams));
        }
    }

    @Override
    public void warning(String message, Object... messageParams) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, MessageFormatter.format(message, messageParams));
        }
    }

    @Override
    public void error(Throwable t, String message, Object... messageParams) {
        if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, MessageFormatter.format(message, messageParams), t);
        }
    }
}
