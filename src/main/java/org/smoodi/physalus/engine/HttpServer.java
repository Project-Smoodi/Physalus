package org.smoodi.physalus.engine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.Port;
import org.smoodi.physalus.SocketListeningPort;
import org.smoodi.physalus.status.Stated;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class HttpServer implements Ported, Stated {

    /**
     * <p>{@link State#NONE}, {@link State#SETTING}, {@link State#STARTING}, {@link State#RUNNING}, {@link State#STOPPING}, {@link State#STOPPED}</p>
     */
    @Getter
    private State state = State.NONE;

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
