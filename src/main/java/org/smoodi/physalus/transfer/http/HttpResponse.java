package org.smoodi.physalus.transfer.http;

import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.physalus.transfer.Response;

/**
 * <p>The HTTP response for send to the client.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
public interface HttpResponse extends Response {

    /**
     * <p>Return the content of response.</p>
     *
     * <p>Depending on the {@code Content-Type} in {@link HttpResponse#getHeaders() http headers}, it can be cast as a specific type.</p>
     *
     * <p>Like this:</p>
     * <table>
     *     <thead>
     *         <td><b>Content-Type</b></td>
     *         <td><b>Java Type</b></td>
     *     </thead>
     *     <tr>
     *         <td>application/json</td>
     *         <td>Object</td>
     *     </tr>
     *     <tr>
     *         <td>text/html</td>
     *         <td>String</td>
     *     </tr>
     *     <tr>
     *         <td>text/plain</td>
     *         <td>String</td>
     *     </tr>
     * </table>
     *
     * @return The content of response.
     */
    @Nullable
    @Override
    Object getContent();

    HttpResponse json(@NotNull Object valueObject);

    /**
     * <p>Return headers of response.</p>
     *
     * @return Headers of response.
     * @see MapHttpHeaders
     */
    @EmptyableArray
    @NotNull
    @Override
    HttpHeaders getHeaders();

    /**
     * Return status code of response.
     * <p>
     * If didn't {@link HttpResponse#setStatusCode(HttpStatus) set status code}, it will return {@link HttpStatus#OK 200 OK}.
     *
     * @return Status code of response.
     * @see HttpStatus
     */
    @NotNull
    HttpStatus getStatusCode();

    /**
     * <p>Set status code of Response, and return {@code this} for method chaining.</p>
     *
     * @param status Status code
     * @return {@code this}
     */
    HttpResponse setStatusCode(@NotNull HttpStatus status);

    /**
     * <p>Set content of Response, and return {@code this} for method chaining.</p>
     *
     * @param content      Content
     * @param contentType Type of content on parameter
     * @return {@code this}
     */
    HttpResponse setContent(@Nullable Object content, @Nullable ContentType contentType);

    // TODO("내가 만든 Cookie")

    /**
     * <p>Finalize response, and freezing.</p>
     *
     * <p>Set HTTP standard headers, make desultory data to correctly, etc.</p>
     */
    void finalization();

    /**
     * <p>Create general HTTP response value object.</p>
     *
     * @param address The address of client, for create empty response object.
     * @return New HTTP response value object.
     */
    static HttpResponse withAddress(@NotNull String address) {
        return AbstractHttpResponse.Default.withAddress(address);
    }
}
