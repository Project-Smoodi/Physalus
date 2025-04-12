package org.smoodi.physalus;

import ch.qos.logback.classic.Level;
import org.smoodi.physalus.configuration.Configuration;
import org.smoodi.physalus.configuration.ConfigurationManager;
import org.smoodi.physalus.configuration.LoggerConfiguration;
import org.smoodi.physalus.engine.Physalus;

public abstract class BaseStarter implements Starter {

    @Override
    public final void start() {
        Physalus.instance();

        configLogger();

        startInner();
    }

    private void configLogger() {
        Configuration loggerConfiguration = new LoggerConfiguration(Level.INFO);
        ConfigurationManager.applyConfiguration(loggerConfiguration);
    }

    protected abstract void startInner();
}
