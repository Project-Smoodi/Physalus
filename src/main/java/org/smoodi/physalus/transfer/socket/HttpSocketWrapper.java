package org.smoodi.physalus.transfer.socket;

import org.smoodi.physalus.transfer.http.*;

import java.io.IOException;
import java.net.Socket;

public class HttpSocketWrapper implements HttpSocket {

    private static final String PROTOCOL = "HTTP/1.1";

    private final HttpExchange exchange;

    private final IOStreamSocket socket;

    public HttpSocketWrapper(IOStreamSocket socket) throws IOException {
        this.socket = socket;

        this.exchange = new SocketBasedHttpExchange(socket);
    }

    @Override

    public HttpExchange getExchange() {
        return this.exchange;
    }

    @Override
    public HttpRequest getRequest() {
        return this.getExchange().getRequest();
    }

    @Override
    public HttpResponse getResponse() {
        return this.getExchange().getResponse();
    }

    @Override
    public void doResponse() throws SocketShutdownException {
        var writer = this.socket.getOutput();
        var response = this.exchange.getResponse();

        response.finish();

        try {
            writer.write(ResponseSerializer.serialize(response));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new SocketShutdownException(e);
        }
    }

    @Override
    public Socket toNative() {
        return this.socket.toNative();
    }

    @Override
    public void close() throws SocketShutdownException {
        this.socket.close();
    }

    @Override
    public boolean isClosed() {
        return this.socket.isClosed();
    }

    @Override
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    @Override
    public boolean isBound() {
        return this.socket.isBound();
    }
}
