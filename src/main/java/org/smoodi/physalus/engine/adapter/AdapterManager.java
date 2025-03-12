package org.smoodi.physalus.engine.adapter;

import org.smoodi.physalus.exchange.Exchange;

import java.util.HashSet;
import java.util.Set;

public class AdapterManager implements Adapted {

    private final Set<Adapter> adapters = new HashSet<>();

    public void resolve(Exchange exchange, String tag) {
    }

    @Override
    public boolean addAdapter(Adapter adapter) {
        if (adapters.contains(adapter)) {
            return false;
        }

        adapters.add(adapter);
        return false;
    }

    @Override
    public boolean removeAdapter(Adapter adapter) {
        if (adapters.contains(adapter)) {
            adapters.remove(adapter);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAdapter(String tag) {
        if (adapters.isEmpty()) {
            return false;
        }

        return adapters.removeIf(it -> it.getTag().contains(tag));
    }
}
