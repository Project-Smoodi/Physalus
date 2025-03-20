package org.smoodi.physalus;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerConfiguration {

    private static final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    private static boolean initialized = false;

    public static void configureLogback() {
        if (initialized) {
            return;
        }

        configureDefault();

        initialized = true;
    }

    private static void configureDefault() {
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
