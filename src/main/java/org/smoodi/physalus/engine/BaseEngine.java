package org.smoodi.physalus.engine;

import lombok.Getter;
import org.smoodi.physalus.engine.adapter.Adapted;
import org.smoodi.physalus.engine.adapter.Adapter;
import org.smoodi.physalus.engine.port.Port;
import org.smoodi.physalus.engine.port.Ported;
import org.smoodi.physalus.status.Stated;

public abstract class BaseEngine
        implements Engine, ListeningEngine, Ported, Adapted, Stated {

    /**
     * <p>{@link State#NONE}, {@link State#INITIALIZING}, {@link State#STARTING}, {@link State#RUNNING}, {@link State#STOPPING}, {@link State#STOPPED}</p>
     */
    @Getter
    protected State state = State.NONE;

    protected abstract Ported ported();

    protected abstract Adapted adapted();


    @Override
    public boolean addAdapter(Adapter adapter) {
        return adapted().addAdapter(adapter);
    }

    @Override
    public boolean removeAdapter(Adapter adapter) {
        return adapted().removeAdapter(adapter);
    }

    @Override
    public boolean removeAdapter(String tag) {
        return adapted().removeAdapter(tag);
    }

    @Override
    public boolean addPort(Port port) {
        return ported().addPort(port);
    }

    @Override
public boolean addPort(int port) {
        return ported().addPort(port);
    }

    @Override
    public boolean removePort(Port port) {
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
