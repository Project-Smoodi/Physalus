package org.smoodi.physalus.configuration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager implements Configuration {

    private static final List<Configuration> configurations = new ArrayList<>();

    public static void addConfiguration(final Configuration configuration) {
        configurations.add(configuration);
    }

    @Override
    public void config() {
        configurations.forEach(Configuration::config);
    }
}
