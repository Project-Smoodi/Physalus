package org.smoodi.physalus.engine.adapter;

import org.smoodi.annotation.NotNull;
import org.smoodi.physalus.transfer.Exchange;

import java.util.*;

public class PhysalusAdapterManager implements AdapterContext {

    private final Set<Adapter> adapters = new LinkedHashSet<>();

    public void execute(@NotNull Exchange exchange, @NotNull String tag) {
        assert exchange != null;
        assert tag != null;

        for (Adapter adapter : adapters) {
            if (Objects.equals(adapter.getTag(), tag)) {
                adapter.execute(exchange);
            }
        }
    }

    @Override
    public boolean addAdapter(@NotNull Adapter adapter) {
        assert adapter != null;
        return adapters.add(adapter);
    }

    @Override
    public boolean removeAdapter(@NotNull Adapter adapter) {
        assert adapter != null;
        return adapters.remove(adapter);
    }

    @Override
    public boolean removeAdapter(String tag) {
        assert tag != null;
        return adapters.removeIf(it -> it.getTag().contains(tag));
    }
}
