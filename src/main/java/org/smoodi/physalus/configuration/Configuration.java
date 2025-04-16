package org.smoodi.physalus.configuration;

public interface Configuration {

    void config();

    default void apply() {
        ConfigurationManager.applyConfiguration(this);
    }
}
