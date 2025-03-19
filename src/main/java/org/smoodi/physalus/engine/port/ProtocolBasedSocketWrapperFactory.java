package org.smoodi.physalus.engine.port;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.transfer.socket.HttpSocketWrapper;
import org.smoodi.physalus.transfer.socket.Socket;
import org.smoodi.physalus.transfer.socket.SocketWrapper;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProtocolBasedSocketWrapperFactory {

    public static Socket wrap(java.net.Socket socket, String tag) throws IOException {
        if (tag.equals(Tagged.StandardTags.HTTP.value)
                || tag.equals(Tagged.StandardTags.HTTPS.value)) {
            return new HttpSocketWrapper(new SocketWrapper(socket));
        } else if (tag.equals(Tagged.StandardTags.TCP.value)) {
            return new SocketWrapper(socket);
        } else {
            throw new IllegalArgumentException("Unknown tag: " + tag);
        }
    }
}
