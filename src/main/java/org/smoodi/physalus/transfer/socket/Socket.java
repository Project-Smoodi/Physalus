package org.smoodi.physalus.transfer.socket;

/**
 * <p>A Classical Socket with TCP.</p>
 *
 * @author Daybreak312
 */
public interface Socket {

    java.net.Socket toNative();

    void close() throws SocketShutdownException;

    boolean isClosed();

    boolean isConnected();

    boolean isBound();
}
