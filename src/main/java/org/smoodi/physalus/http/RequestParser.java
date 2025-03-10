package org.smoodi.physalus.http;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.engine.port.SocketWrapper;
import org.smoodi.physalus.exchange.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestParser {

    public static HttpRequest parse(SocketWrapper socket) throws IOException {
        RequestTemp temp = new RequestTemp();
        var reader = socket.getInput();

        major(reader, temp);

        headers(reader, temp);

        body(reader, temp);

        return temp.toAvailable();
    }

    private static void major(BufferedReader reader, RequestTemp temp) throws IOException {
        var input = reader.readLine();
        var split = input.split(" ");
        if (!split[2].startsWith("HTTP")) {
            throw new IllegalArgumentException("Not a HTTP request.");
        }
        temp.method = HttpMethod.valueOf(split[0]);
        String[] pathParam = split[1].split("\\?");
        temp.path = pathParam[0];
        temp.protocol = split[2];

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
            temp.params = Map.copyOf(params);
        } else {
            temp.params = Map.of();
        }
    }

    private static void headers(BufferedReader reader, RequestTemp temp) throws IOException {
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
        temp.headers = new UnmodifiableMapHttpHeaders(headers);
    }

    private static void body(BufferedReader reader, RequestTemp temp) throws IOException { // Body
        if (temp.headers.get(HttpHeaderNames.CONTENT_LENGTH) == null) {
            int contentLength = Integer.parseInt(temp.headers.get(HttpHeaderNames.CONTENT_LENGTH));
            char[] bodyBuffer = new char[contentLength];
            int bytesRead = reader.read(bodyBuffer, 0, contentLength);

            if (bytesRead != contentLength) {
                throw new IOException("Body was not fully received.");
            }

            temp.content = new String(bodyBuffer);
        } else {
            temp.content = "";
        }
    }

    private static void url(BufferedReader reader, RequestTemp temp) throws IOException {
        temp.host = temp.headers.get(HttpHeaderNames.HOST);
        temp.url = "url";
        temp.uri = temp.host + temp.path;
        temp.port = Integer.parseInt(temp.host.split(":")[1]);
    }

    @Getter
    private static final class RequestTemp implements HttpRequest {

        public String address;

        public String url;

        public String uri;

        public String host;

        public String path;

        public String protocol;

        public HttpMethod method;

        public int port;

        public Map<String, String> params;

        public HttpHeaders headers;

        public Object content;

        @Override
        public boolean isSecureProtocol() {
            throw new UnsupportedOperationException("This is not active object, just temp vo.");
        }

        public HttpRequest toAvailable() {
            return new SocketBasedHttpExchange.Request(address, url, uri, host, path, protocol, method, port, params, headers, content);
        }
    }
}
