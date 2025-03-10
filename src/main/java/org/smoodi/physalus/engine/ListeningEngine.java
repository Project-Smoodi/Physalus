package org.smoodi.physalus.engine;

import org.smoodi.physalus.engine.port.SocketWrapper;

import java.util.List;

public interface ListeningEngine {

    void doService(SocketWrapper socket, List<String> tags);
}
