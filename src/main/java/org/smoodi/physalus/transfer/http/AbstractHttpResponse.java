package org.smoodi.physalus.transfer.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.smoodi.annotation.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <p>The first implementation of HttpResponse.</p>
 *
 * <p>This class provide 2 fundamental features:</p>
 * <ul>
 *     <li>1. Freezing Object.</li>
 *     <li>2. Finalization progress for HTTP Standards</li>
 * </ul>
 *
 * @author Daybreak312
 * @since v0.1.0 ALPHA
 */
@Getter
public abstract class AbstractHttpResponse implements HttpResponse {

    protected static ObjectMapper mapper = new ObjectMapper();

    private boolean finish = false;

    private final String address;

    private HttpStatus statusCode = HttpStatus.OK;

    private HttpHeaders headers = new MapHttpHeaders();

    private Object content = null;

    public AbstractHttpResponse(@NotNull String address) {
        assert address != null;

        this.address = address;
    }

    @Override
    public final HttpResponse setStatusCode(HttpStatus statusCode) {
        checkFrozen();

        this.statusCode = statusCode;

        return this;
    }

    @Override
    public void setContent(Object content) {
        checkFrozen();

        this.content = content;
    }

    @Override
    public HttpResponse setContent(Object content, ContentType contentType) {
        checkFrozen();

        this.content = content;
        getHeaders().set(HttpHeaderNames.CONTENT_TYPE, contentType.value);

        return this;
    }

    @Override
    public HttpResponse json(@NotNull Object valueObject) {
        checkFrozen();

        if (valueObject == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        this.content = valueObject;
        this.headers.set(HttpHeaderNames.CONTENT_TYPE, ContentType.APPLICATION_JSON.value);

        return this;
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
                this.content = mapper.writeValueAsString(this.content);
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

    public static final class Default extends AbstractHttpResponse {

        public Default(@NotNull String address) {
            super(address);
        }

        public static HttpResponse withAddress(@NotNull String address) {
            return new Default(address);
        }
    }
}
