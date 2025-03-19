package org.smoodi.physalus.engine.port;

public interface PortContext {

    boolean addPort(PortValue port);

    boolean addPort(int port);

    boolean removePort(PortValue port);

    boolean removePort(int port);

    boolean removePort(String tag);
}
