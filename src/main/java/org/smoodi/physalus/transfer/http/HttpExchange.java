package org.smoodi.physalus.transfer.http;

import org.smoodi.physalus.transfer.Exchange;

public interface HttpExchange extends Exchange {

    HttpRequest getRequest();

    HttpResponse getResponse();
}
