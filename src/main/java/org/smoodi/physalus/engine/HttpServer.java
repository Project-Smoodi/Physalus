package org.smoodi.physalus.engine;

import org.smoodi.physalus.transfer.socket.HttpSocket;

public interface HttpServer {

    /**
     * <p>Start the http server.</p>
     */
    void startServer();

    /**
     * <p>Stop the http server.</p>
     */
    void stopServer();

    /**
     * <p>Response to the client of the socket's data</p>
     */
    void response(HttpSocket socket);

    /**
     * <p>Join current thread to server's runtime thread.</p>
     *
     * <p>The current thread will be blocking after call this method.</p>
     */
    void listening();
}
