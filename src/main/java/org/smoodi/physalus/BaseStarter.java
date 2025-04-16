package org.smoodi.physalus;

import ch.qos.logback.classic.Level;
import org.smoodi.physalus.configuration.Configuration;
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
        Configuration config = new LoggerConfiguration(Level.INFO);
        config.apply();
    }

    protected abstract void startInner();
}
