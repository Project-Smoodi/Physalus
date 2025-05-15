package org.smoodi.physalus.transfer.http;

import org.smoodi.physalus.transfer.socket.IOStreamSocket;
import org.smoodi.physalus.transfer.socket.SocketShutdownException;

public final class ResponseSender {

    public static void sendResponse(HttpResponse response, IOStreamSocket socket) {
        try {
            response.finalization();

            socket.getOutput().write(ResponseSerializer.serialize(response));
            socket.getOutput().flush();
            socket.getOutput().close();
        } catch (Exception e) {
            throw new SocketShutdownException("Failed to send response", e);
        }
    }
}
