package org.smoodi.physalus.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import lombok.Setter;
import org.slf4j.LoggerFactory;

public final class LoggerConfiguration implements SetupConfiguration {

    private final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    private boolean initialized = false;

    @Setter
    private Level level = Level.INFO;

    @Setter
    private String pattern = defaultPattern();

    public LoggerConfiguration() {
    }

    public LoggerConfiguration(final Level level) {
        this.level = level;
    }

    public LoggerConfiguration(final String pattern) {
        this.pattern = pattern;
    }

    public LoggerConfiguration(final Level level, final String pattern) {
        this.level = level;
        this.pattern = pattern;
    }

    @Override
    public synchronized void config() {
        if (initialized) return;
        initialized = true;

        configure0();
    }

    private void configure0() {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(context);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(this.pattern);
        encoder.start();

        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(this.level);
        rootLogger.detachAndStopAllAppenders();
        rootLogger.addAppender(consoleAppender);
    }

    private String defaultPattern() {
        return "%d{yyyy-MM-dd HH:mm:ss.SSS} " +
                "%highlight(%-5level) " +
                "[%-20thread{20}] " +
                "%cyan(%-38logger{38}) " +
                ":: " +
                "%msg%n";
    }
}
