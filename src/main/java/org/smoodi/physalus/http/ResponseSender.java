package org.smoodi.physalus.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.exchange.HttpResponse;

import java.io.IOException;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseSender {

    private static final String PROTOCOL = "HTTP/1.1";

    public static void send(final SocketWrapper socket, final HttpResponse response) throws IOException {
        var writer = socket.getOutput();

        writer.write(PROTOCOL + " " + response.getStatusCode().status + " " + response.getStatusCode().reason + "\r\n");
        for (Map.Entry<String, String> entry : response.getHeaders().toMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            writer.write(key + ": " + value + "\r\n");
        }
        writer.write(response.getContent().toString() + "\r\n");

        writer.flush();
        writer.close();
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
