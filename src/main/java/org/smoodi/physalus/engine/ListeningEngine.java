package org.smoodi.physalus.engine;

import org.smoodi.physalus.transfer.socket.HttpSocket;

public interface ListeningEngine {

    void doService(HttpSocket socket, String tag);
}
