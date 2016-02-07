package org.denominator.gotthard.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class BriefLogFormatter extends Formatter {
    private static final ThreadLocal<SimpleDateFormat> format  = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        }
    };
    private static final String                        lineSep = System.getProperty("line.separator");

    public static String formatRecord(LogRecord record) {
        String loggerName = record.getLoggerName();
        if (loggerName == null) {
            loggerName = "root";
        }
        StringBuilder output = new StringBuilder()
                .append("[")
                .append(record.getLevel()).append('|')
                .append(Thread.currentThread().getName()).append('|')
                .append(format.get().format(new Date(record.getMillis())))
                .append("]: ")
                .append(record.getMessage())
                .append(" | ")
                .append(loggerName)
                .append(lineSep);

        @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
        final Throwable throwable = record.getThrown();
        if (throwable != null) {
            final StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            output.append(sw.toString());
        }
        return output.toString();
    }

    public String format(LogRecord record) {
        return formatRecord(record);
    }
}
