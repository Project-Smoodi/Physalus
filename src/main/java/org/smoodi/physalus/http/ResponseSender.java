package org.smoodi.physalus.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.exchange.HttpRequest;
import org.smoodi.physalus.exchange.HttpResponse;

import java.io.IOException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseSender {

    public static void send(final SocketWrapper socket, final HttpResponse response) {

    }

    public static void send(final SocketWrapper socket, final HttpRequest request, final HttpResponse response) {
    }

    public static void sendOK(final SocketWrapper socket) throws IOException {
        var writer = socket.getOutput();

        writer.write("HTTP/1.1 200 OK\r\n");
        writer.write("\r\n");
        writer.flush();
        writer.close();
    }

    public static void sendBadRequest(final SocketWrapper socket) throws IOException {
        var writer = socket.getOutput();

        writer.write("HTTP/1.1 400 Bad Request\r\n");
        writer.newLine();
        writer.write("Content-Type: text/plain\r\n");
        writer.write("Connection: close\r\n");
        writer.write("Smoodi: physalus\r\n");
        writer.newLine();
        writer.write("\r\n");
        writer.flush();
        writer.close();
    }
}
