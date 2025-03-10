package org.smoodi.physalus.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.http.RequestParser;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

@Getter
public final class SocketBasedHttpExchange
        implements HttpExchange {

    private final SocketWrapper socket;

    private final HttpRequest request;

    // TODO("To HttpResponse")
    private final Response response;

    public SocketBasedHttpExchange(SocketWrapper socket) throws IOException {
        checkSocketAvailable(socket.get());
        this.socket = socket;

        this.request = RequestParser.parse(socket);
        this.response = new Response();
    }


    private void checkSocketAvailable(Socket socket) {
        if (socket.isClosed()) {
            throw new IllegalArgumentException("Socket is not available; it's closed.");
        } else if (!socket.isConnected()) {
            throw new IllegalArgumentException("Socket is not available; it isn't connected.");
        } else if (socket.isInputShutdown()) {
            throw new IllegalArgumentException("Socket is not available; it's input shutdown.");
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Request implements HttpRequest {

        private final String address;

        private final String url;

        private final String uri;

        private final String host;

        private final String path;

        private final String protocol;

        private final HttpMethod method;

        private final int port;

        @UnmodifiableArray
        private final Map<String, String> params;

        @UnmodifiableArray
        private final HttpHeaders headers;

        @Nullable
        private final Object content;

        @Override
        public boolean isSecureProtocol() {
            return false;
        }
    }

    public static class Response {
    }

}
