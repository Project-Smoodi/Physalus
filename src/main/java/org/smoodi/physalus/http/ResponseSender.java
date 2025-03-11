package org.smoodi.physalus.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.exchange.HttpResponse;
import org.smoodi.physalus.exchange.HttpStatus;
import org.smoodi.physalus.exchange.SocketBasedHttpExchange;

import java.io.IOException;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseSender {

    private static final String PROTOCOL = "HTTP/1.1";

    public static void send(final SocketWrapper socket, final HttpResponse response) throws IOException {
        var writer = socket.getOutput();

        response.finish();

        writer.write(PROTOCOL + " " + response.getStatusCode().status + " " + response.getStatusCode().reason + "\r\n");
        for (Map.Entry<String, String> entry : response.getHeaders().toMap().entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        if (response.getContent() != null) {
            writer.write("\r\n");
            writer.write(response.getContent().toString() + "\r\n");
        }

        writer.write("\r\n");

        writer.flush();
        writer.close();
    }

    public static void sendOK(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.OK));
    }

    public static void sendCreated(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.CREATED));
    }

    public static void sendNoContent(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.NO_CONTENT));
    }

    public static void sendBadRequest(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.BAD_REQUEST));
    }

    public static void sendUnauthorized(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.UNAUTHORIZED));
    }

    public static void sendNotFound(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.NOT_FOUND));
    }

    public static void sendInternalServerError(final SocketWrapper socket) throws IOException {
        send(socket, createStatusResponse(socket, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private static HttpResponse createStatusResponse(final SocketWrapper socket, final HttpStatus status)    {
        final HttpResponse response = new SocketBasedHttpExchange.Response(socket.get().getRemoteSocketAddress().toString());

        response.setStatusCode(status);
        response.setContent(null);

        return response;
    }
}
