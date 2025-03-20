package org.smoodi.physalus.engine.port;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.annotation.NotNull;
import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.engine.ListeningEngine;
import org.smoodi.physalus.status.Stated;
import org.smoodi.physalus.transfer.socket.HttpSocket;
import org.smoodi.physalus.transfer.socket.SocketShutdownException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class ServerRuntime implements PortContext, Stated {

    private static final List<String> ALLOWED_TAGS = List.of(
            Tagged.StandardTags.HTTP.value,
            Tagged.StandardTags.HTTPS.value
    );

    /**
     * <p>{@link State#NONE}, {@link State#SETTING}, {@link State#STARTING}, {@link State#RUNNING}, {@link State#STOPPING}, {@link State#STOPPED}</p>
     */
    @Getter
    private State state = State.NONE;

    private final Set<Port> ports = new HashSet<>();

    private final ListeningEngine engine;

    private final ThreadFactory factory = Thread.ofVirtual().factory();

    private final List<Thread> listeningThreads = new ArrayList<>();

    public ServerRuntime(ListeningEngine engine) {
        this.engine = engine;
    }

    public synchronized void startServer() {
        checkSetup();

        this.state = State.STARTING;

        doListening();

        this.state = State.RUNNING;

        log.info("Physalus Server started. ports: {}", ports.stream().map(Port::getPortNumber).toList());
    }

    private void checkSetup() {
        if (this.state != State.SETTING) {
            if (this.state == State.RUNNING) {
                throw new IllegalStateException("Server is already started.");
            }
            if (is(this.state, State.STOPPING, State.STOPPED)) {
                throw new IllegalStateException("Server is stopped. Cannot start server.");
            }

            if (this.ports.isEmpty()) {
                this.state = State.ERRORED;
                throw new IllegalStateException("No ports available.");
            }
            if (engine == null) {
                this.state = State.ERRORED;
                throw new IllegalStateException("No engine available.");
            }

            throw new IllegalStateException("Something went wrong. Server was not set.");
        }
    }

    private void doListening() {
        ports.forEach(port -> {
            final Thread thread = factory.newThread(() -> {
                log.info("Listening on port {}", port.getPortNumber());

                while (true) {
                    if (Thread.interrupted()) {
                        return;
                    }

                    var socket = port.accept();

                    if (socket instanceof HttpSocket socket1) {
                        engine.doService(socket1, port.getTag());
                    } else {
                        socket.close();
                    }
                }
            });
            thread.setName("port-listening-" + port.getPortNumber());
            thread.start();
            listeningThreads.add(thread);
        });
    }

    public void response(@NotNull HttpSocket socket) {
        assert socket != null;

        if (socket.isClosed()) {
            return;
        }

        try {
            socket.doResponse();
        } catch (SocketShutdownException e) {
            log.error(e.getMessage(), e);
        } finally {
            socket.close();
        }
    }

    /**
     * <p>Stop the server.</p>
     *
     * <p>Before call this function, you must finish all requests(Socket). </p>
     */
    public synchronized void stopServer() {
        if (this.state != State.RUNNING) {
            throw new IllegalStateException("Server is not running.");
        }

        listeningThreads.forEach(Thread::interrupt);

        ports.forEach(Port::close);
    }

    @Override
    public boolean addPort(Port port) {
        setting();
        if (ports.stream().anyMatch(port::equals)) {
            return false;
        }
        if (!ALLOWED_TAGS.contains(port.getTag())) {
            throw new IllegalArgumentException("PortValue number " + port.getPortNumber() + " with type \"" + port.getTag() + "\" is not allowed. Allowed port types: " + ALLOWED_TAGS);
        }

        this.ports.add(port);
        return true;
    }

    @Override
    public boolean addPort(int port) {
        setting();
        return addPort(HttpPort.of(port));
    }

    @Override
    public boolean removePort(Port port) {
        setting();
        return ports.removeIf(port::equals);
    }

    @Override
    public boolean removePort(int port) {
        setting();
        return ports.removeIf(it -> it.getPortNumber() == port);
    }

    @Override
    public boolean removePort(String tag) {
        setting();
        return ports.removeIf(it -> it.getTag().contains(tag));
    }

    private void setting() {
        if (this.state == State.NONE) {
            this.state = State.SETTING;
        }
    }
}
