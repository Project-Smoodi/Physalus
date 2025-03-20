package org.smoodi.physalus.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public final class LoggerConfiguration implements Configuration {

    private final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    private boolean initialized = false;

    @Override
    public void config() {
        if (initialized) {
            return;
        }

        configure0();

        initialized = true;
    }

    private void configure0() {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(context);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(
                "%d{yyyy-MM-dd HH:mm:ss.SSS} " +
                        "%highlight(%-5level) " +
                        "[%-20thread{20}] " +
                        "%cyan(%-38logger{38}) " +
                        ":: " +
                        "%msg%n"
        );
        encoder.start();

        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.detachAndStopAllAppenders();
        rootLogger.addAppender(consoleAppender);
    }
}
