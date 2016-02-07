package org.denominator.gotthard.logging;

import org.denominator.junit.LangAssert;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.denominator.gotthard.logging.LoggingManager.*;
import static org.junit.Assert.assertNotNull;

public class LoggingTest {
    @Test
    public void utilityClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(LoggingManager.class);
        LangAssert.assertUtilityClass(LoggerFactory.class);
    }

    @Test
    public void jdkLogger() throws IOException {
        final org.denominator.gotthard.logging.Logger logger = LoggerFactory.getLogger(LoggingTest.class);

        initLoggingProperties(allLevels());
        logger.info("Info {}", 1);
        logger.trace("Trace {}", 1);
        logger.warning("Warning {}", 1);
        logger.error(new RuntimeException(), "Error {}", 1);
    }

    @Test
    public void directFormatter() {
        final BriefLogFormatter formatter = new BriefLogFormatter();
        final LogRecord record = new LogRecord(Level.INFO, "Message");
        assertNotNull(formatter.format(record));
    }

    @Test
    public void briefLogFormatter() throws IOException {
        initLoggingProperties(consoleInfoOnly());
        Logger logger = Logger.getLogger(LoggingTest.class.getName());
        logger.log(Level.INFO, "Test", new RuntimeException("test"));
        initLoggingProperties(consoleWarningOnly());
        logger.log(Level.WARNING, "Test", new RuntimeException("test"));
        initLoggingProperties(consoleSevereOnly());
        logger.log(Level.SEVERE, "Test", new RuntimeException("test"));
        initLoggingProperties(consoleSevereOnly());
        Logger.getAnonymousLogger().severe("Test");
        initDefaultLogging();
    }

    @Test
    public void consoleHandler() throws IOException {
        new ConsoleStdoutHandler();

        initLoggingProperties("handlers=" + ConsoleStdoutHandler.class.getName() + "\n" +
                ".level=INFO\n" +
                ConsoleStdoutHandler.class.getName() + ".level=INFO");
        LoggerFactory.getLogger(getClass()).info("Test message");

        initLoggingProperties(consoleInfoOnly());
        LoggerFactory.getLogger(getClass()).info("Test message");
    }
}
