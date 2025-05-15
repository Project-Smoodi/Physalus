package org.smoodi.physalus.engine.port;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.StaticFactoryMethod;
import org.smoodi.physalus.transfer.StandardPorts;
import org.smoodi.physalus.transfer.http.HttpResponse;
import org.smoodi.physalus.transfer.http.HttpStatus;
import org.smoodi.physalus.transfer.http.ResponseSender;
import org.smoodi.physalus.transfer.socket.HttpSocketWrapper;
import org.smoodi.physalus.transfer.socket.IOStreamSocket;
import org.smoodi.physalus.transfer.socket.Socket;
import org.smoodi.physalus.transfer.socket.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class HttpPort implements Port {

    @NotNull
    @Getter(onMethod_ = {@Override})
    private final int portNumber;

    @NotNull
    @Getter(onMethod_ = {@Override})
    private final String tag;

    @NotNull
    private final ServerSocket serverSocket;

    public HttpPort(int portNumber) {
        if (portNumber == StandardPorts.HTTP.portNumber || portNumber == StandardPorts.HTTP_.portNumber) {
            this.tag = StandardTags.HTTP.value;
        } else if (portNumber == StandardPorts.HTTPS.portNumber) {
            this.tag = StandardTags.HTTPS.value;
        } else {
            throw new IllegalArgumentException("Invalid port number: " + portNumber + "; Only HTTP and HTTPS are supported.");
        }
        this.portNumber = portNumber;

        try {
            this.serverSocket = new ServerSocket(portNumber);
            log.debug("{} socket is anchored on number {}", this.tag, portNumber);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not listen on port: " + portNumber, e);
        }
    }

    @StaticFactoryMethod
    public static HttpPort of(int portNumber) {
        return new HttpPort(portNumber);
    }

    public boolean isBound() {
        return serverSocket.isBound();
    }

    public Socket accept() {
        while (true) {
            try (java.net.Socket raw = serverSocket.accept()) {
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
                } catch (IllegalArgumentException e) {
                    sendBadRequest(socket);
                } catch (Exception e) {
                    log.error("Could not accept socket", e);
                }

            } catch (IOException ignore) {
            }
        }
    }

    private void sendBadRequest(IOStreamSocket socket) {
        var res = HttpResponse.withAddress(socket.toNative().getRemoteSocketAddress().toString());
        res.setStatusCode(HttpStatus.BAD_REQUEST);
        ResponseSender.sendResponse(res, socket);
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
        if (obj instanceof HttpPort port) {
            return port.getPortNumber() == getPortNumber() && port.serverSocket == serverSocket;
        }

        if (obj instanceof Port port) {
            return port.getPortNumber() == getPortNumber();
        }

        return false;
    }
}
