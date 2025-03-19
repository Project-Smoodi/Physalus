package org.smoodi.physalus.transfer.socket;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SocketShutdownException extends RuntimeException {

    public SocketShutdownException(final String message) {
        super(message);
    }

    public SocketShutdownException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SocketShutdownException(final Throwable cause) {
        super(cause);
    }
}
