package org.smoodi.physalus;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;


public class SocketListeningPort implements Tagged {

    private final Port value;

    private final ServerSocket serverSocket;

    public SocketListeningPort(Port port) {
        this.value = port;
        try {
            this.serverSocket = new ServerSocket(port.getPortNumber());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not listen on port: " + port.getPortNumber());
        }
    }

    public static SocketListeningPort of(Port port) {
        return new SocketListeningPort(port);
    }

    @Override
    public List<String> getTag() {
        return value.getTag();
    }

    public int getPortNumber() {
        return value.getPortNumber();
    }

    public boolean isBound() {
        return serverSocket.isBound();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Port port) {
            return port.getPortNumber() == getPortNumber();
        }

        if (obj instanceof SocketListeningPort port) {
            return port.getPortNumber() == getPortNumber() && port.serverSocket == serverSocket;
        }

        return false;
    }
}
