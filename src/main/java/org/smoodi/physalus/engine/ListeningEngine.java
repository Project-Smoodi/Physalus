package org.smoodi.physalus.engine;

import java.net.Socket;
import java.util.List;

public interface ListeningEngine {

    void doService(Socket socket, List<String> tags);
}
