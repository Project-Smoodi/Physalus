package org.smoodi.physalus.transfer.socket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.Socket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JavaSocketUtils {

    public static void close(Socket socket) {
        if (socket.isClosed() || !socket.isConnected() || !socket.isBound()) {
            return;
        }

        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
