package org.smoodi.physalus.transfer.http;

import lombok.Getter;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.UnmodifiableArray;

import java.util.Map;

/**
 * <p>The first implementation of HttpRequest.</p>
 *
 * <p>This abstract aim to immutable, so this doesn't have any setter, or ways for modify.</p>
 *
 * @since v0.1.0 ALPHA
 * @author Daybreak312
 */
@Getter
public abstract class AbstractHttpRequest implements HttpRequest {

    private final String address;

    private final String url;

    private final String uri;

    private final String host;

    private final String path;

    private final String protocol;

    private final HttpMethod method;

    private final int port;

    @UnmodifiableArray
    private final Map<String, String> params;

    @UnmodifiableArray
    private final HttpHeaders headers;

    @Nullable
    private final Object content;

    public AbstractHttpRequest(
            @NotNull String address,
            @NotNull String url,
            @NotNull String uri,
            @NotNull String host,
            @NotNull String path,
            @NotNull String protocol,
            @NotNull HttpMethod method,
            @NotNull int port,
            @Nullable Map<String, String> params,
            @NotNull @EmptyableArray HttpHeaders headers,
            @Nullable Object content
    ) {
        assert address != null;
        assert url != null;
        assert uri != null;
        assert host != null;
        assert path != null;
        assert protocol != null;
        assert method != null;
        assert port >= 0;
        assert headers != null;

        this.address = address;
        this.url = url;
        this.uri = uri;
        this.host = host;
        this.path = path;
        this.protocol = protocol;
        this.method = method;
        this.port = port;
        this.params = params;
        this.headers = headers;
        this.content = content;
    }

    @Override
    public boolean isSecureProtocol() {
        return false;
    }


    public static final class Default extends AbstractHttpRequest {
        public Default(
                @NotNull String address,
                @NotNull String url,
                @NotNull String uri,
                @NotNull String host,
                @NotNull String path,
                @NotNull String protocol,
                @NotNull HttpMethod method,
                @NotNull int port,
                @Nullable Map<String, String> params,
                @NotNull @EmptyableArray HttpHeaders headers,
                @Nullable Object content
        ) {
            super(address, url, uri, host, path, protocol, method, port, params, headers, content);
        }
    }
}
