package org.smoodi.physalus.transfer.http;

import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.Overload;
import org.smoodi.physalus.transfer.Headers;

/**
 * <p>Wrapper of HTTP headers saved as Key-Value {@link java.util.Collection Collection}.</p>
 *
 * <p>Include more getters than {@link Headers}; by {@link HttpHeaderNames}</p>
 *
 * @author Daybreak312
 * @see HttpHeaderNames
 * @see Headers
 * @since v0.0.1
 */
public interface HttpHeaders extends Headers {

    @Nullable
    @Overload
    String get(@NotNull final HttpHeaderNames key);

    @Overload
    void set(@NotNull final HttpHeaderNames key, final String value);

    @Nullable
    String contentType();

    @Nullable
    String contentEncoding();

    @Nullable
    String contentLength();

    @Nullable
    String contentLanguage();

    @Nullable
    String authorization();

    @Nullable
    String location();
}
