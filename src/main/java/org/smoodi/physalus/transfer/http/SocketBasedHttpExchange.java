package org.smoodi.physalus.transfer.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.transfer.socket.IOStreamSocket;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Getter
public final class SocketBasedHttpExchange
        implements HttpExchange {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final IOStreamSocket socket;

    private final HttpRequest request;

    // TODO("To HttpResponse")
    private final HttpResponse response;

    public SocketBasedHttpExchange(IOStreamSocket socket) throws IOException {
        checkSocketAvailable(socket);
        this.socket = socket;

        this.request = RequestParser.parse(socket);
        this.response = new Response(this.request.getAddress());
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

    @Getter
    public static class Response implements HttpResponse {

        private boolean finish = false;

        private final String address;

        private HttpStatus statusCode = HttpStatus.OK;

        private HttpHeaders headers = new MapHttpHeaders();

        private Object content = null;

        public Response(String address) {
            this.address = address;
        }

        @Override
        public void setStatusCode(HttpStatus statusCode) {
            checkFrozen();

            this.statusCode = statusCode;
        }

        public void setContent(Object content) {
            checkFrozen();

            this.content = content;
        }

        @Override
        public void json(@NotNull Object valueObject) {
            checkFrozen();

            if (valueObject == null) {
                throw new IllegalArgumentException("Argument is null.");
            }
            this.content = valueObject;
            this.headers.set(HttpHeaderNames.CONTENT_TYPE, ContentType.APPLICATION_JSON.value);
        }

        @Override
        public void finish() {
            if (this.finish)
                return;
            this.finish = true;

            finishContent();
            finishHeader();
        }

        private void finishContent() {
            if (this.content == null) return;

            if (Objects.equals(this.headers.contentType(), ContentType.APPLICATION_JSON.value)) {
                try {
                    this.content = objectMapper.writeValueAsString(this.content);
                } catch (JsonProcessingException e) {
                    throw new IllegalArgumentException("Cannot serialize content to JSON; Content's classname: " + this.content.getClass().getSimpleName(), e);
                }
            }
        }

        private void finishHeader() {
            // TODO("Keep-Alive 구현 후 제거")
            this.headers.set(HttpHeaderNames.CONNECTION, "close");

            this.headers.set(HttpHeaderNames.DATE.name, OffsetDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));

            if (this.content != null) {
                this.headers.set(HttpHeaderNames.CONTENT_LENGTH, "" + this.content.toString().length());

                if (this.headers.contentType() == null) {
                    if (this.content instanceof String) {
                        this.headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8");
                    } else {
                        throw new IllegalArgumentException("Content type not configured.");
                    }
                }
            }

            this.headers = new UnmodifiableMapHttpHeaders(headers);
        }

        private void checkFrozen() {
            if (finish) {
                throw new UnsupportedOperationException("Cannot modify response, Response is frozen.");
            }
        }
    }
}
