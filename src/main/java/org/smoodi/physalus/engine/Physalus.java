package org.smoodi.physalus.engine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.engine.adapter.AdapterContext;
import org.smoodi.physalus.engine.adapter.PhysalusAdapterManager;
import org.smoodi.physalus.engine.port.PortContext;
import org.smoodi.physalus.engine.port.ServerRuntime;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.transfer.http.HttpExchange;
import org.smoodi.physalus.transfer.http.SocketBasedHttpExchange;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadFactory;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Physalus
        extends BaseEngine {

    private static Physalus engine;

    @Getter
    private final ServerRuntime serverRuntime = new ServerRuntime(this);

    @Getter
    private final AdapterContext adapterManager = new PhysalusAdapterManager();

    private final ThreadFactory threadFactory = Thread.ofVirtual().name("request-resolver-", 0).factory();

    @Override
    protected PortContext ported() {
        return serverRuntime;
    }

    @Override
    protected AdapterContext adapterContext() {
        return adapterManager;
    }

    public static Engine instance() {
        if (engine == null) {
            engine = new Physalus();
        }

        return engine;
    }

    @Override
    public void startEngine() {
        serverRuntime.startServer();

        log.info("Physalus, The engine started. at: {}", LocalDateTime.now());
    }

    @Override
    public void stopEngine() {
        serverRuntime.stopServer();

        log.info("Physalus, The engine stopped. at: {}", LocalDateTime.now());
    }

    @Override
    public void doService(SocketWrapper socket, String tag) {
        threadFactory.newThread(() -> {
            HttpExchange exchange = null;
            try {
                // TODO("요청에 대한 처리")
                exchange = SocketBasedHttpExchange.of(socket);

                mainController(exchange, tag);

                log.debug("Application processing finished. Socket@{}", socket.hashCode());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            serverRuntime.response(exchange, socket);
        }).start();
    }

    public void mainController(HttpExchange exchange, String tag) {

        // TODO("Filtering...")

        adapterManager.execute(exchange, tag);
    }
}
