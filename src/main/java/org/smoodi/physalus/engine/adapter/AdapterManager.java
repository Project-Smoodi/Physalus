package org.smoodi.physalus.engine.adapter;

import org.smoodi.physalus.transfer.Exchange;

@FunctionalInterface
public interface AdapterManager {

    void execute(Exchange exchange, String tag);
}
