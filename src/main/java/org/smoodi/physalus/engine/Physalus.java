package org.smoodi.physalus.engine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.engine.adapter.Adapter;
import org.smoodi.physalus.engine.port.Port;
import org.smoodi.physalus.engine.adapter.Adapted;
import org.smoodi.physalus.engine.adapter.AdapterManager;
import org.smoodi.physalus.engine.port.Ported;
import org.smoodi.physalus.engine.port.ServerRuntime;
import org.smoodi.physalus.exchange.Request;

import java.net.Socket;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Physalus
        implements Engine, ListeningEngine, Adapted, Ported {

    @Getter
    private State state;

    private static Physalus engine;

    private final ServerRuntime serverRuntime = new ServerRuntime();

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
        serverRuntime.startServer();
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
        return serverRuntime.addPort(port);
    }

    @Override
    public boolean addPort(int port) {
        return serverRuntime.addPort(port);
    }

    @Override
    public boolean removePort(Port port) {
        return serverRuntime.removePort(port);
    }

    @Override
    public boolean removePort(int port) {
        return serverRuntime.removePort(port);
    }

    @Override
    public boolean removePort(String tag) {
        return serverRuntime.removePort(tag);
    }
}
