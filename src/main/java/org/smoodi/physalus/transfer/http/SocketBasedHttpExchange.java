package org.smoodi.physalus.transfer.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.smoodi.physalus.transfer.socket.IOStreamSocket;

import java.io.IOException;

@Getter
public final class SocketBasedHttpExchange
        implements HttpExchange {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final IOStreamSocket socket;

    private final HttpRequest request;

    private final HttpResponse response;

    public SocketBasedHttpExchange(IOStreamSocket socket) throws IOException {
        checkSocketAvailable(socket);
        this.socket = socket;

        this.request = RequestParser.parse(socket);
        this.response = HttpResponse.of(this.request.getAddress());
    }

    public static HttpExchange of(IOStreamSocket socket) throws IOException {
        return new SocketBasedHttpExchange(socket);
    }

    private void checkSocketAvailable(IOStreamSocket socket) {
        if (socket.isClosed()) {
            throw new IllegalArgumentException("Socket is not available; it's closed.");
        } else if (!socket.isConnected()) {
            throw new IllegalArgumentException("Socket is not available; it isn't connected.");
        } else if (socket.isInputShutdown()) {
            throw new IllegalArgumentException("Socket is not available; it's input shutdown.");
        }
    }
}
