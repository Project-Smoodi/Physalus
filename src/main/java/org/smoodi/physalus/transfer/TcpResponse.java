package org.smoodi.physalus.transfer;

import lombok.RequiredArgsConstructor;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.ModifiableArray;

@RequiredArgsConstructor
public class TcpResponse implements Response {

    private final String ipAddress;

    private final byte ipAddressVersion;

    private Object content;

    @ModifiableArray
    private final Headers headers = MapHeaders.empty();

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

    @Override
    public void setContent(@Nullable Object content) {
        this.content = content;
    }

    @EmptyableArray
    @ModifiableArray
    @NotNull
    @Override
    public Headers getHeaders() {
        return this.headers;
    }
}
