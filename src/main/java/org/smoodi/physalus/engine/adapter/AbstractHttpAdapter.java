package org.smoodi.physalus.engine.adapter;

import org.smoodi.annotation.NotNull;
import org.smoodi.physalus.exchange.Exchange;
import org.smoodi.physalus.exchange.HttpExchange;

import java.util.Objects;

public abstract class AbstractHttpAdapter implements Adapter {

    @Override
    public final void execute(@NotNull Exchange exchange) {
        if (Objects.requireNonNull(exchange) instanceof HttpExchange) {
            execute(exchange);
        }
        throw new IllegalArgumentException("Unsupported exchange type: " + exchange.getClass().getName());
    }

    public abstract void resolve(@NotNull HttpExchange exchange);
}
