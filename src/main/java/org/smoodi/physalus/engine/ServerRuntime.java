package org.smoodi.physalus.engine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.Port;
import org.smoodi.physalus.SocketListeningPort;
import org.smoodi.physalus.status.Stated;

import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ServerRuntime implements Ported, Stated {

    /**
     * <p>{@link State#NONE}, {@link State#SETTING}, {@link State#STARTING}, {@link State#RUNNING}, {@link State#STOPPING}, {@link State#STOPPED}</p>
     */
    @Getter
    private State state = State.NONE;

    private final Set<SocketListeningPort> ports = new HashSet<>();

    private ListeningEngine engine;

    private List<Thread> listeningThreads;

    public synchronized void startServer() {
        checkSetup();

        this.state = State.STARTING;

        doListening();

        this.state = State.RUNNING;
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
        final var factory = Thread.ofVirtual().factory();

        this.ports.forEach(it -> {
            final Thread thread = factory.newThread(() -> {
                Socket socket;
                while (true) {
                    socket = it.accept();

                    if (Thread.interrupted()) {
                        return;
                    }

                    engine.doService(socket, it.getTag());
                }
            });
            thread.setName("port-listening-" + it.getPortNumber());
            listeningThreads.add(thread);
        });
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

        ports.forEach(SocketListeningPort::close);
    }

    @Override
    public boolean addPort(Port port) {
        setting();
        if (ports.stream().anyMatch(port::equals)) {
            return false;
        }
        ports.add(SocketListeningPort.of(port));
        return true;
    }

    @Override
    public boolean addPort(int port) {
        setting();
        return addPort(new Port(port));
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
