package org.smoodi.physalus.engine;

import lombok.Getter;
import org.smoodi.physalus.engine.adapter.AdapterContext;
import org.smoodi.physalus.engine.port.Port;
import org.smoodi.physalus.engine.port.PortContext;
import org.smoodi.physalus.status.Stated;

public abstract class BaseEngine
        implements RunningEngine, ListeningEngine, PortContext, Stated {

    /**
     * <p>{@link State#NONE}, {@link State#INITIALIZING}, {@link State#STARTING}, {@link State#RUNNING}, {@link State#STOPPING}, {@link State#STOPPED}</p>
     */
    @Getter(onMethod_ = @Override)
    protected State state = State.NONE;

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
}
