package org.smoodi.physalus.transfer.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>표준 HTTP 헤더 이름들.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
@Getter
@AllArgsConstructor
public enum HttpHeaderNames {

    // Auth
    WWW_AUTHENTICATE("WWW-Authenticate"),
    AUTHORIZATION("Authorization"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),

    // Cache
    AGE("Age"),
    CACHE_CONTROL("Cache-Control"),
    CLEAR_SITE_DATA("Clear-Site-Data"),
    EXPIRES("Expires"),
    PRAGMA("Pragma"),
    WARNING("Warning"),

    // If
    LAST_MODIFIED("Last-Modified"),
    ETAG("ETag"),
    IF_MATCH("If-Match"),
    IF_NONE_MATCH("If-None-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    VARY("Vary"),

    // Connection
    CONNECTION("Connection"),
    KEEP_ALIVE("Keep-Alive"),

    // Cookie
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),

    // CORS
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
    ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
    ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
    ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
    ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
    ORIGIN("Origin"),
    TIMING_ALLOW_ORIGIN("Timing-Allow-Origin"),

    // Content
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LOCATION("Content-Location"),

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),

    // Redirection
    LOCATION("Location"),

    // Request
    FROM("From"),
    HOST("Host"),
    REFERER("Referer"),
    REFERER_POLICY("Referer-Policy"),
    USER_AGENT("User-Agent"),

    // Response
    ALLOW("Allow"),
    SERVER("ListeningServer"),

    // Security
    CROSS_ORIGIN_RESOURCE_POLICY("Cross-Origin-Resource-Policy"),
    CONTENT_SECURITY_POLICY("Content-Security-Policy"),
    CONTENT_SECURITY_POLICY_REPORT_ONLY("Content-Security-Policy-Report-Only"),

    // Range
    ACCEPT_RANGES("Accept-Ranges"),
    RANGE("Range"),
    IF_RANGE("If-Range"),
    CONTENT_RANGE("Content-Range"),

    // Others
    DATE("Date"),
    ;

    final String name;

    @Override
    public String toString() {
        return name;
    }
}
