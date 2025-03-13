package org.smoodi.physalus.transfer;

import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.UnmodifiableArray;

/**
 * <p>The request sent by client.</p>
 *
 * @author Daybreak312
 * @see Response
 * @since v0.0.1
 */
public interface Request {

    /**
     * <p>Return the Address of Client.</p>
     *
     * @return The Address of client. ex) {@code 127.0.0.1}(IPv4)
     */
    @NotNull
    String getAddress();

    /**
     * <p>Return the content of request.</p>
     *
     * @return The content of request.
     */
    @Nullable
    Object getContent();

    /**
     * <p>Return headers of request.</p>
     *
     * @return Headers of request.
     * @see Headers
     */
    @EmptyableArray
    @UnmodifiableArray
    @NotNull
    Headers getHeaders();
}
