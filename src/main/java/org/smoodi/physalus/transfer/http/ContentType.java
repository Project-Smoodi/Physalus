package org.smoodi.physalus.transfer.http;

import lombok.AllArgsConstructor;

/**
 * <p>Content type of HTTP packet.</p>
 *
 * <p>It's included in {@code Content-Type} header.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
@AllArgsConstructor
public enum ContentType {

    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),

    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    TEXT_JAVASCRIPT("text/javascript"),
    TEXT_XML("text/xml"),
    TEXT_PLAIN("text/plain"),

    MULTIPART_FORM_DATA("multipart/form-data"),
    MULTIPART_MIXED("multipart/mixed"),
    MULTIPART_RELATED("multipart/related"),
    ;

    public final String value;
}
