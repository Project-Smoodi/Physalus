package org.smoodi.physalus.engine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.Adapter;
import org.smoodi.physalus.Port;
import org.smoodi.physalus.exchange.Request;

import java.net.Socket;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Physalus
        implements Engine, ListeningEngine, Adapted, Ported {

    @Getter
    private State state;

    private static Physalus engine;

    private final HttpServer httpServer = new HttpServer();

    private final AdapterManager adapterModule = new AdapterManager() {
    };

    public static Engine instance() {
        if (engine == null) {
            engine = new Physalus();
        }

        return engine;
    }

    @Override
    public void startEngine() {
        httpServer.startServer();
    }

    @Override
    public void stopEngine() {

    }

    @Override
    public void doService(Socket socket, List<String> tags) {

    }

    public void mainController(Request request, List<String> tags) {

    }

    @Override
    public boolean addAdapter(Adapter adapter) {
        return adapterModule.addAdapter(adapter);
    }

    @Override
    public boolean removeAdapter(Adapter adapter) {
        return adapterModule.removeAdapter(adapter);
    }

    @Override
    public boolean removeAdapter(String tag) {
        return adapterModule.removeAdapter(tag);
    }

    @Override
    public boolean addPort(Port port) {
        return httpServer.addPort(port);
    }

    @Override
    public boolean addPort(int port) {
        return httpServer.addPort(port);
    }

    @Override
    public boolean removePort(Port port) {
        return httpServer.removePort(port);
    }

    @Override
    public boolean removePort(int port) {
        return httpServer.removePort(port);
    }

    @Override
    public boolean removePort(String tag) {
        return httpServer.removePort(tag);
    }
}
