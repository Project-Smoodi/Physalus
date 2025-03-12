package org.smoodi.physalus.engine.port;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class SocketWrapper {

    private final Socket socket;

    public Socket get() {
        return socket;
    }

    @Getter
    private final BufferedReader input;

    @Getter
    private final BufferedWriter output;

    public SocketWrapper(Socket socket) {
        this.socket = socket;

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            if (socket.isInputShutdown()) {
                throw new IllegalArgumentException("Socket is not available; it's input shutdown.", e);
            } else if (socket.isOutputShutdown()) {
                throw new IllegalArgumentException("Socket is not available; it's output shutdown.", e);
            } else if (socket.isClosed()) {
                throw new IllegalArgumentException("Socket is not available; it's closed.", e);
            } else {
                throw new IllegalArgumentException("Socket is not available; it's errored.", e);
            }
        }
    }
}
