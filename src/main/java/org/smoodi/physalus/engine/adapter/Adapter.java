package org.smoodi.physalus.engine.adapter;

import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.exchange.Exchange;

public interface Adapter extends Tagged {

    void execute(Exchange exchange);
}
