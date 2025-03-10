package org.smoodi.physalus.exchange;

import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.Overload;

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
