package org.smoodi.physalus.engine.adapter;

import org.smoodi.physalus.transfer.Exchange;

import java.util.HashSet;
import java.util.Set;

public class PhysalusAdapterManager implements AdapterContext {

    private final Set<Adapter> adapters = new HashSet<>();

    public void execute(Exchange exchange, String tag) {
    }

    @Override
    public boolean addAdapter(Adapter adapter) {
        if (adapters.contains(adapter)) {
            return false;
        }

        adapters.add(adapter);
        return true;
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
