package org.smoodi.physalus.engine.adapter;

import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.exchange.Response;

public interface Adapter extends Tagged {

    Response resolve();
}
