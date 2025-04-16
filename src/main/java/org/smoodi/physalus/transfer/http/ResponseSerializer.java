package org.smoodi.physalus.transfer.http;

import java.util.Map;

public final class ResponseSerializer {

    private static final String PROTOCOL = "HTTP/1.1";

    public static String serialize(final HttpResponse response) {
        final StringBuilder builder = new StringBuilder();

        // [0] HTTP/1.1 [1] 200 [2] OK
        builder.append(PROTOCOL + " ")
                .append(response.getStatusCode().status).append(" ")
                .append(response.getStatusCode().reason).append("\r\n");

        for (Map.Entry<String, String> entry : response.getHeaders().toMap().entrySet()) {
            // Header-Name: Header-Value
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        if (response.getContent() != null) {
            builder.append("\r\n");
            builder.append(response.getContent().toString()).append("\r\n");
        }

        // finish.
        builder.append("\r\n");

        return builder.toString();
    }
}
