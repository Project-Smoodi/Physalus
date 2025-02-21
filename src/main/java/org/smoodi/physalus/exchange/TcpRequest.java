package org.smoodi.physalus.exchange;

import lombok.RequiredArgsConstructor;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.UnmodifiableArray;

@RequiredArgsConstructor
public class TcpRequest implements Request {

    private final String ipAddress;

    private final byte ipAddressVersion;

    private final Object content;

    @UnmodifiableArray
    private final Headers headers;

    @NotNull
    @Override
    public String getAddress() {
        return this.ipAddress;
    }

    @Nullable
    @Override
    public Object getContent() {
        return this.content;
    }

    @EmptyableArray
    @UnmodifiableArray
    @NotNull
    @Override
    public Headers getHeaders() {
        return this.headers;
    }
}
