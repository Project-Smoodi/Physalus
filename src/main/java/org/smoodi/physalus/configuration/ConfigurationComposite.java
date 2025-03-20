package org.smoodi.physalus.configuration;

import java.util.List;

public class ConfigurationComposite implements Configuration {

    private final List<Configuration> configurations = List.of(
            new LoggerConfiguration()
    );

    @Override
    public void config() {
        configurations.forEach(Configuration::config);
    }
}
