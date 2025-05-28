package org.smoodi.physalus.engine;

import lombok.Getter;
import org.smoodi.physalus.engine.adapter.Adapter;
import org.smoodi.physalus.engine.adapter.AdapterContext;
import org.smoodi.physalus.engine.port.Port;
import org.smoodi.physalus.engine.port.PortContext;

public abstract class BaseEngine implements ServerEngine {

    @Getter(onMethod_ = @Override)
    protected State state = State.SLEEPING;

    protected abstract PortContext portContext();

    protected abstract AdapterContext adapterContext();

    @Override
    public boolean addPort(Port port) {
        return portContext().addPort(port);
    }

    @Override
    public boolean addPort(int port) {
        return portContext().addPort(port);
    }

    @Override
    public boolean removePort(Port port) {
        return portContext().removePort(port);
    }

    @Override
    public boolean removePort(int port) {
        return portContext().removePort(port);
    }

    @Override
    public boolean removePort(String tag) {
        return portContext().removePort(tag);
    }

    @Override
    public boolean addAdapter(Adapter adapter) {
        return adapterContext().addAdapter(adapter);
    }

    @Override
    public boolean removeAdapter(Adapter adapter) {
        return adapterContext().removeAdapter(adapter);
    }

    @Override
    public boolean removeAdapter(String tag) {
        return adapterContext().removeAdapter(tag);
    }
}
