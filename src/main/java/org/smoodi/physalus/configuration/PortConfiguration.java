package org.smoodi.physalus.configuration;

import org.smoodi.physalus.engine.Physalus;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>The configuration for add listener on port.</p>
 *
 * @author Daybreak312
 * @since v0.1.0 ALPHA
 */
public class PortConfiguration implements SetupConfiguration {

    private static Map<Integer, String> ports = new HashMap<>();

    public void addPort(int port) {
        ports.put(port, "" + null);
    }

    public void addPort(int port, String tag) {
        if (!tag.equalsIgnoreCase("http") && !tag.equalsIgnoreCase("https")) {
            throw new IllegalArgumentException("Invalid tag: " + tag + "; Physalus only supports http(s).");
        }
        ports.put(port, tag);
    }

    @Override
    public void config() {
        Physalus engine = (Physalus) Physalus.instance();

        ports.forEach((key, value) -> {
            if (value != null) {
                engine.addPort(key);
            }
        });
    }
}
