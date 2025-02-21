package org.smoodi.physalus.exchange;

import lombok.AllArgsConstructor;

/**
 * <p>표준 HTTP 메소드.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
@AllArgsConstructor
public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    ;

    public final String value;
}
