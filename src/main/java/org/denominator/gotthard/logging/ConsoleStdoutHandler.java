package org.denominator.gotthard.logging;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;

public final class ConsoleStdoutHandler extends ConsoleHandler {
    @Override
    protected synchronized void setOutputStream(OutputStream out) throws SecurityException {
        super.setOutputStream(System.out);
    }
}
