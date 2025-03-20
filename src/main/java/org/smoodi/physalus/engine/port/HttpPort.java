package org.smoodi.physalus.engine.port;

import lombok.extern.slf4j.Slf4j;
import org.smoodi.annotation.NotNull;
import org.smoodi.physalus.transfer.socket.HttpSocketWrapper;
import org.smoodi.physalus.transfer.socket.IOStreamSocket;
import org.smoodi.physalus.transfer.socket.Socket;
import org.smoodi.physalus.transfer.socket.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class HttpPort implements Port {

    private final PortValue value;

    @NotNull
    private final ServerSocket serverSocket;

    public HttpPort(PortValue port) {
        this.value = port;
        try {
            this.serverSocket = new ServerSocket(port.getPortNumber());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not listen on port: " + port.getPortNumber(), e);
        }
    }

    public static HttpPort of(PortValue port) {
        return new HttpPort(port);
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
        while (true) {
            try {
                java.net.Socket raw = serverSocket.accept();
                IOStreamSocket socket = new SocketWrapper(raw);

                { // Check it is http request stream or not
                    socket.getInput().mark(1);
                    if (socket.getInput().read() == -1) {
                        socket.close();
                        continue;
                    } else {
                        socket.getInput().reset();
                    }
                }

                try { // Try parsing request
                    return new HttpSocketWrapper(socket);
                } catch (Exception e) {
                    log.error("Could not accept socket", e);
                    raw.close();
                }
            } catch (IOException ignore) {
            }
            // TODO("Stackoverflow 괜찮나?")
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
        if (obj instanceof PortValue port) {
            return port.getPortNumber() == getPortNumber();
        }

        if (obj instanceof HttpPort port) {
            return port.getPortNumber() == getPortNumber() && port.serverSocket == serverSocket;
        }

        return false;
    }
}
