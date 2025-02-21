package org.smoodi.physalus.exchange;

import lombok.NoArgsConstructor;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.Overload;
import org.smoodi.annotation.StaticFactoryMethod;
import org.smoodi.annotation.array.EmptyArray;
import org.smoodi.annotation.array.EmptyableArray;

import java.util.Map;

/**
 * <p>Wrapper class of HTTP headers saved as {@link Map Map}.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
@NoArgsConstructor
public class MapHttpHeaders
        extends MapHeaders
        implements HttpHeaders {

    public MapHttpHeaders(@NotNull final Map<String, String> headers) {
        super(headers);
    }

    public MapHttpHeaders(@NotNull final Headers headers) {
        super(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static MapHttpHeaders of(@NotNull final Map<String, String> headers) {
        return new MapHttpHeaders(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static MapHttpHeaders of(@NotNull final Headers headers) {
        return new MapHttpHeaders(headers);
    }

    @EmptyArray
    @NotNull
    @StaticFactoryMethod
    public static MapHttpHeaders empty() {
        return new MapHttpHeaders();
    }

    @Nullable
    @Overload
    @Override
    public String get(@NotNull final HttpHeaderNames key) {
        return get(key.toString());
    }

    @Nullable
    @Override
    public String contentType() {
        return get(HttpHeaderNames.CONTENT_TYPE);
    }

    @Nullable
    @Override
    public String contentEncoding() {
        return get(HttpHeaderNames.CONTENT_ENCODING);
    }

    @Nullable
    @Override
    public String contentLanguage() {
        return get(HttpHeaderNames.CONTENT_LANGUAGE);
    }

    @Nullable
    @Override
    public String authorization() {
        return get(HttpHeaderNames.AUTHORIZATION);
    }

    @Nullable
    @Override
    public String location() {
        return get(HttpHeaderNames.LOCATION);
    }
}
