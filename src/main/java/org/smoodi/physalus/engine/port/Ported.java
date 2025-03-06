package org.smoodi.physalus.engine.port;

public interface Ported {

    boolean addPort(Port port);

    boolean addPort(int port);

    boolean removePort(Port port);

    boolean removePort(int port);

    boolean removePort(String tag);
}
