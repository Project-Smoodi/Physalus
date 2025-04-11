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
 * @since v0.1.0 ALPHA
 * @author Daybreak312
 */
@Getter
public abstract class AbstractHttpResponse implements HttpResponse {

    protected static ObjectMapper mapper = new ObjectMapper();

    private boolean finish = false;

    private final String address;

    private HttpStatus statusCode = HttpStatus.OK;

    private HttpHeaders headers = new MapHttpHeaders();

    private Object content = null;

    public AbstractHttpResponse(String address) {
        this.address = address;
    }

    @Override
    public final HttpResponse setStatusCode(HttpStatus statusCode) {
        checkFrozen();

        this.statusCode = statusCode;
    }

    @Override
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
}
