package org.smoodi.physalus.exchange;

public interface HttpExchange extends Exchange {

    HttpRequest getRequest();

    HttpResponse getResponse();
}
