package org.smoodi.physalus.transfer.socket;

import org.smoodi.physalus.transfer.http.HttpExchange;
import org.smoodi.physalus.transfer.http.HttpRequest;
import org.smoodi.physalus.transfer.http.HttpResponse;

public interface HttpSocket extends Socket {

    HttpExchange getExchange();

    HttpRequest getRequest();

    HttpResponse getResponse();

}
