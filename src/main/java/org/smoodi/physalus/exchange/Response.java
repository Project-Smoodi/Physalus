package org.smoodi.physalus.exchange;

import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.ModifiableArray;

/**
 * <p>The response for send to the client.</p>
 *
 * @author Daybreak312
 * @see Request
 * @since v0.0.1
 */
public interface Response {

    /**
     * <p>Return the Address of Client.</p>
     *
     * @return The Address of client. ex) {@code 127.0.0.1}(IPv4)
     */
    @NotNull
    String getAddress();

    /**
     * <p>Return the content of response.</p>
     *
     * @return The content of response.
     */
    @Nullable
    Object getContent();

    /**
     * <p>Set content of the response to be sent to the client.</p>
     *
     * @param content Content of the response for set.
     */
    void setContent(@Nullable Object content);

    /**
     * <p>Return headers of response.</p>
     *
     * @return Headers of response.
     * @see Headers
     */
    @EmptyableArray
    @ModifiableArray
    @NotNull
    Headers getHeaders();
}
