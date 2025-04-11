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
        return adapters.add(adapter);
    }

    @Override
    public boolean removeAdapter(Adapter adapter) {
        return adapters.remove(adapter);
    }

    @Override
    public boolean removeAdapter(String tag) {
        return adapters.removeIf(it -> it.getTag().contains(tag));
    }
}
