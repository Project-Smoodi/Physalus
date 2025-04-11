package org.smoodi.physalus.engine.adapter;

import org.smoodi.annotation.NotNull;
import org.smoodi.physalus.transfer.Exchange;

@FunctionalInterface
public interface AdapterManager {

    void execute(@NotNull Exchange exchange, @NotNull String tag);
}
