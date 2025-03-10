package org.smoodi.physalus;

import lombok.AllArgsConstructor;

import java.util.List;

public interface Tagged {

    List<String> getTag();

    @AllArgsConstructor
    enum StandardTags {

        HTTP("HTTP"),
        HTTPS("HTTPS"),
        TCP("TCP"),
        TEST("Test"),
        ;

        public final String value;
    }
}
