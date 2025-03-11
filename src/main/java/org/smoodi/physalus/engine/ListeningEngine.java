package org.smoodi.physalus.engine;

import org.smoodi.physalus.engine.port.SocketWrapper;

public interface ListeningEngine {

    void doService(SocketWrapper socket, String tag);
}
