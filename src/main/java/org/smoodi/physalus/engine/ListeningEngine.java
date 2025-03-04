package org.smoodi.physalus.engine;

import org.smoodi.physalus.exchange.Request;

import java.util.List;

public interface ListeningEngine {

    void doService(Request request, List<String> tags);
}
