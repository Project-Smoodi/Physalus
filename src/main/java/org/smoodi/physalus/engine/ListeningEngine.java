package org.smoodi.physalus.engine;

import org.smoodi.physalus.transfer.socket.SocketWrapper;

public interface ListeningEngine {

    void doService(SocketWrapper socket, String tag);
}
