package org.smoodi.physalus.engine.port;

import lombok.extern.slf4j.Slf4j;
import org.smoodi.annotation.NotNull;
import org.smoodi.physalus.Tagged;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


@Slf4j
public class SocketListeningPort implements Tagged {

    private final Port value;

    @NotNull
    private final ServerSocket serverSocket;

    public SocketListeningPort(Port port) {
        this.value = port;
        try {
            this.serverSocket = new ServerSocket(port.getPortNumber());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not listen on port: " + port.getPortNumber(), e);
        }
    }

    public static SocketListeningPort of(Port port) {
        return new SocketListeningPort(port);
    }

    @Override
    public String getTag() {
        return value.getTag();
    }

    public int getPortNumber() {
        return value.getPortNumber();
    }

    public boolean isBound() {
        return serverSocket.isBound();
    }

    public Socket accept() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not accept connection: " + e.getMessage());
        }
    }

    public void close() {
        if (serverSocket.isClosed()) {
            return;
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO("더 찾아보고 추가적인 처리 코드 작성할 것")
            log.error(e.getMessage(), e);
        }
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
