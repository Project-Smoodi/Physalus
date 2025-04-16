package org.smoodi.physalus.engine.adapter;

import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.transfer.Exchange;

public interface Adapter extends Tagged {

    void execute(Exchange exchange);
}
