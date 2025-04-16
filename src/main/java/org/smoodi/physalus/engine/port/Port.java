package org.smoodi.physalus.engine.port;

import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.transfer.socket.Socket;


public interface Port extends Tagged {

    int getPortNumber();

    boolean isBound();

    Socket accept();

    void close();
}
