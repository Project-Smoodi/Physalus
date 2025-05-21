package org.smoodi.physalus.engine;

import org.smoodi.physalus.status.Stated;
import org.smoodi.physalus.transfer.socket.HttpSocket;

public interface Engine extends Stated {

    void startEngine();

    void stopEngine();

    void doService(HttpSocket socket, String tag);

    void listening();
}
