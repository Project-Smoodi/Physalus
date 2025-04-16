package org.smoodi.physalus;

import ch.qos.logback.classic.Level;
import org.smoodi.physalus.configuration.LoggerConfiguration;
import org.smoodi.physalus.configuration.PortConfiguration;

public class TestStarter extends BaseStarter {

    @Override
    protected void startInner() {
        configLogger();
        configHttpServer();
    }

    private void configLogger() {
        LoggerConfiguration config = new LoggerConfiguration(Level.DEBUG);
        config.apply();
    }

    private void configHttpServer() {
        PortConfiguration config = new PortConfiguration();
        config.addPort(8080);
        config.apply();
    }
}
