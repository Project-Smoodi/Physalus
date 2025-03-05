package org.smoodi.physalus.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smoodi.physalus.Port;
import org.smoodi.physalus.SocketListeningPort;
import org.smoodi.physalus.status.Stated;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class HttpServer implements Ported, Stated {

    private static final Logger log = LoggerFactory.getLogger(HttpServer.class);
    private final Set<SocketListeningPort> ports = new HashSet<>();

    private ListeningEngine engine;

    private Thread listeningThread;


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

    public void startServer() {
        listeningThread = Thread.ofPlatform()
                .name("http-server")
                .start(() -> this.ports.forEach(it -> {
                    if (it.isBound()) {
                        Socket socket = null;
                        try {
                            socket = it.accept();
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }

                        engine.doService(socket, it.getTag());
                    }
                })); // TODO("스레드 이름 명명")
    }
}
