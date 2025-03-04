package org.smoodi.physalus.engine;

import org.smoodi.physalus.Port;
import org.smoodi.physalus.SocketListeningPort;
import org.smoodi.physalus.status.Stated;

import java.util.HashSet;
import java.util.Set;

public class HttpServer implements Ported, Stated {

    private final Set<SocketListeningPort> ports = new HashSet<>();

    private ListeningEngine engine;

    @Override
    public boolean addPort(Port port) {
        if (ports.stream().anyMatch(port::equals)) {
            return false;
        }
        ports.add(SocketListeningPort.of(port));
        return true;
    }

    @Override
    public boolean addPort(int port) {
        return addPort(new Port(port));
    }

    @Override
    public boolean removePort(Port port) {
        return ports.removeIf(port::equals);
    }

    @Override
    public boolean removePort(int port) {
        return ports.removeIf(it -> it.getPortNumber() == port);
    }

    @Override
    public boolean removePort(String tag) {
        return ports.removeIf(it -> it.getTag().contains(tag));
    }
}
