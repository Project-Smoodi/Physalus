package org.smoodi.physalus;

import org.smoodi.physalus.exchange.Response;

public interface Adapter extends Tagged {

    Response resolve();
}
