package org.denominator.gotthard.logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public final class LoggingManager {
    private static final String DEFAULT_LOGGING_PROPERTIES =
            "handlers=java.util.logging.ConsoleHandler\n" +
                    ".level=$LEVEL$\n" +
                    "java.util.logging.ConsoleHandler.level=$LEVEL$\n" +
                    "java.util.logging.ConsoleHandler.formatter = " + BriefLogFormatter.class.getName() + "\n";

    private LoggingManager() {
        throw new AssertionError();
    }

    public static void initLoggingProperties(InputStream inputStream) throws IOException {
        LogManager.getLogManager().readConfiguration(inputStream);
    }

    public static void initLoggingProperties(String config) throws IOException {
        initLoggingProperties(new ByteArrayInputStream(config.getBytes()));
    }

    public static String consoleWarningOnly() {
        return DEFAULT_LOGGING_PROPERTIES.replace("$LEVEL$", "WARNING");
    }

    public static String consoleSevereOnly() {
        return DEFAULT_LOGGING_PROPERTIES.replace("$LEVEL$", "SEVERE");
    }

    public static String consoleInfoOnly() {
        return DEFAULT_LOGGING_PROPERTIES.replace("$LEVEL$", "INFO");
    }

    public static String allLevels() {
        return DEFAULT_LOGGING_PROPERTIES.replace("$LEVEL$", "ALL");
    }

    public static boolean isLoggingSetExternally() {
        return System.getProperty("java.util.logging.config.file") != null;
    }

    public static void initDefaultLogging() throws IOException {
        if (!isLoggingSetExternally()) {
            initLoggingProperties(consoleInfoOnly());
        }
    }
}
