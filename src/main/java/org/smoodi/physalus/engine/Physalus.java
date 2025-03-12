package org.smoodi.physalus.engine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.engine.adapter.Adapted;
import org.smoodi.physalus.engine.adapter.AdapterManager;
import org.smoodi.physalus.engine.port.Ported;
import org.smoodi.physalus.engine.port.ServerRuntime;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.exchange.HttpExchange;
import org.smoodi.physalus.exchange.SocketBasedHttpExchange;

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
    private final AdapterManager adapterManager = new AdapterManager();

    private final ThreadFactory threadFactory = Thread.ofVirtual().name("request-resolver-", 0).factory();

    @Override
    protected Ported ported() {
        return serverRuntime;
    }

    @Override
    protected Adapted adapted() {
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

        adapterManager.resolve(exchange, tag);
    }
}
