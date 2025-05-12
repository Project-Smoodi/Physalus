package org.smoodi.physalus.configuration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager implements Configuration {

    private static final List<Configuration> configurations = new ArrayList<>();

    private boolean configurated = false;

    public static void applyConfiguration(final Configuration configuration) {
        var config = configurations.stream().filter(it -> it.getClass() == configuration.getClass()).findFirst();
        config.ifPresent(configurations::remove);

        configurations.add(configuration);
    }

    @Override
    public void config() {
        if (configurated) return;
        configurated = true;

        configurations.forEach(Configuration::config);

        configurations.removeIf(it -> it instanceof SetupConfiguration);
    }
}
