package org.smoodi.physalus.exchange;

import lombok.Getter;
import lombok.SneakyThrows;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.engine.port.SocketWrapper;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public final class SocketBasedHttpExchange {

    private final SocketWrapper socket;

    private final Request request;

    private final Response response;

    public SocketBasedHttpExchange(SocketWrapper socket) {
        checkSocketAvailable(socket.get());
        this.socket = socket;

        this.request = new Request(socket);
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

    public static class Request implements HttpRequest {

        @Getter
        private final String url;

        @Getter
        private final String uri;

        @Getter
        private final String host;

        @Getter
        private final String path;

        @Getter
        private final String protocol;

        @Getter
        private final HttpMethod method;

        @Getter
        private final int port;

        @UnmodifiableArray
        @Getter
        private final Map<String, String> params;

        @UnmodifiableArray
        @Getter
        private final HttpHeaders headers;

        @Nullable
        @Getter
        private final Object content;

        @SneakyThrows(IOException.class)
        public Request(SocketWrapper socket) {
            var reader = socket.getInput();
            socket.get().setSoTimeout(10000);

            { // First line; Method, Path and Params, Protocol.
                var input = reader.readLine();
                socket.get().setSoTimeout(0);
                var split = input.split(" ");
                if (!split[2].startsWith("HTTP")) {
                    throw new IllegalArgumentException("Not a HTTP request.");
                }
                this.method = HttpMethod.valueOf(split[0]);
                String[] pathParam = split[1].split("\\?");
                this.path = pathParam[0];
                this.protocol = split[2];

                if (pathParam.length > 1) {
                    Map<String, String> params = new HashMap<>();
                    for (String param : split[1].split("\\?")[1].split("&")) {
                        String[] keyValue = param.split("=");
                        if (keyValue.length != 2) {
                            throw new IllegalArgumentException("One parameter in the request is invalid: \"" + param + "\"");
                        }
                        if (keyValue[0].isBlank()) {
                            throw new IllegalArgumentException("One parameter's key in the request is invalid: \"" + param + "\"");
                        }
                        params.put(keyValue[0], keyValue[1]);
                    }
                    this.params = Map.copyOf(params);
                } else {
                    this.params = Map.of();
                }
            }

            { // Lines of headers
                Headers headers = new MapHttpHeaders();
                String input = reader.readLine();
                while (!input.isBlank()) {
                    var headerSet = input.split(": ");

                    if (headerSet.length > 2) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < headerSet.length; i++) {
                            sb.append(headerSet[i]);
                        }
                        headerSet[1] = sb.toString();
                    }

                    headers.set(headerSet[0], headerSet[1]);
                    input = reader.readLine();
                }
                this.headers = new UnmodifiableMapHttpHeaders(headers);
            }

            { // Body
                if (this.headers.get(HttpHeaderNames.CONTENT_LENGTH) == null) {
                    int contentLength = Integer.parseInt(this.headers.get(HttpHeaderNames.CONTENT_LENGTH));
                    char[] bodyBuffer = new char[contentLength];
                    int bytesRead = reader.read(bodyBuffer, 0, contentLength);

                    if (bytesRead != contentLength) {
                        throw new IOException("Body was not fully received.");
                    }

                    this.content = new String(bodyBuffer);
                } else {
                    this.content = "";
                }
            }

            this.host = this.headers.get(HttpHeaderNames.HOST);
            this.url = "url";
            this.uri = this.host + this.path;
            this.port = Integer.parseInt(this.host.split(":")[1]);
        }

        @Override
        public String getAddress() {
            return "";
        }

        @Override
        public boolean isSecureProtocol() {
            return false;
        }
    }

    public static class Response {
    }

}
