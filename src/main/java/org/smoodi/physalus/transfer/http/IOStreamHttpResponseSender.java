package org.smoodi.physalus.transfer.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.transfer.socket.IOStreamSocket;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IOStreamHttpResponseSender {

    public static void sendInternalServerError(final IOStreamSocket socket) {
        if (socket.isOutputShutdown()) {
            return;
        }

        try {
            socket.getOutput().write(
                    ResponseSerializer.serialize(
                            HttpResponse.of(
                                    socket.toNative().getRemoteSocketAddress().toString()
                            )
                    )
            );
        } catch (IOException ignore) {
        }
    }
}
