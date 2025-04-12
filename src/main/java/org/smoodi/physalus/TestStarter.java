package org.smoodi.physalus;

import org.smoodi.physalus.configuration.ConfigurationManager;
import org.smoodi.physalus.configuration.PortConfiguration;

public class TestStarter extends BaseStarter {

    @Override
    protected void startInner() {
        configHttpServer();
    }

    private void configHttpServer() {
        PortConfiguration config = new PortConfiguration();
        config.addPort(8080);
        ConfigurationManager.applyConfiguration(config);
    }
}
