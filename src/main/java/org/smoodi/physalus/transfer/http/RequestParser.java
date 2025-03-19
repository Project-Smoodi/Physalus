package org.smoodi.physalus.transfer.http;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.transfer.Headers;
import org.smoodi.physalus.transfer.socket.IOStreamSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestParser {

    public static HttpRequest parse(IOStreamSocket socket) throws IOException {
        RequestTemp temp = new RequestTemp();
        var reader = socket.getInput();

        temp.address = socket.toNative().getRemoteSocketAddress().toString();

        major(reader, temp);

        headers(reader, temp);

        body(reader, temp);

        url(temp);

        return temp.toAvailable();
    }

    private static void major(BufferedReader reader, RequestTemp temp) throws IOException {
        // [0] GET [1] /home/info?one=two&three=four [2] HTTP/1.1
        var input = reader.readLine().split(" ");

        if (input.length != 3) {
            throw new IllegalArgumentException("Invalid http message.");
        }

        // /home/info "?" one=two&three=four
        var pathParamsRaw = input[1].split("\\?");

        if (!input[2].startsWith("HTTP")) {
            throw new IllegalArgumentException("Invalid http message.");
        }
        try {
            temp.method = HttpMethod.valueOf(input[0]);
            // "/home/info" ? one=two&three=four
            temp.path = pathParamsRaw[0];
            temp.protocol = input[2];

            // /home/info ? "one=two&three=four"
            if (pathParamsRaw.length > 1) {
                temp.params = getParams(pathParamsRaw[1]);
            } else {
                temp.params = Collections.emptyMap();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid http message.", e);
        }
    }

    @UnmodifiableArray
    private static Map<String, String> getParams(String paramString) {
        Map<String, String> temp = new HashMap<>();

        for (String param : paramString.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("One parameter in the request is invalid: \"" + param + "\"");
            }
            if (keyValue[0].isBlank()) {
                throw new IllegalArgumentException("One parameter's key in the request is invalid: \"" + param + "\"");
            }
            temp.put(keyValue[0], keyValue[1]);
        }
        return Map.copyOf(temp);
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
        if (temp.headers.contentLength() != null) {
            int contentLength = Integer.parseInt(temp.headers.contentLength());
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

    private static void url(RequestTemp temp) {
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
