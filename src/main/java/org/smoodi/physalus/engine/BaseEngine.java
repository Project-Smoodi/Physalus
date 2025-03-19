package org.smoodi.physalus.engine;

import lombok.Getter;
import org.smoodi.physalus.engine.adapter.AdapterContext;
import org.smoodi.physalus.engine.port.PortValue;
import org.smoodi.physalus.engine.port.PortContext;
import org.smoodi.physalus.status.Stated;

public abstract class BaseEngine
        implements Engine, ListeningEngine, PortContext, Stated {

    /**
     * <p>{@link State#NONE}, {@link State#INITIALIZING}, {@link State#STARTING}, {@link State#RUNNING}, {@link State#STOPPING}, {@link State#STOPPED}</p>
     */
    @Getter
    protected State state = State.NONE;

    protected abstract PortContext ported();

    protected abstract AdapterContext adapterContext();

    @Override
    public boolean addPort(PortValue port) {
        return ported().addPort(port);
    }

    @Override
    public boolean addPort(int port) {
        return ported().addPort(port);
    }

    @Override
    public boolean removePort(PortValue port) {
        return ported().removePort(port);
    }

    @Override
    public boolean removePort(int port) {
        return ported().removePort(port);
    }

    @Override
    public boolean removePort(String tag) {
        return ported().removePort(tag);
    }
}
