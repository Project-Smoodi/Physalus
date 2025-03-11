package org.smoodi.physalus;

import lombok.AllArgsConstructor;
import org.smoodi.annotation.NotNull;

import java.util.List;

public interface Tagged {

    @NotNull
    String getTag();

    @AllArgsConstructor
    enum StandardTags {

        HTTP("HTTP"),
        HTTPS("HTTPS"),
        TCP("TCP"),
        TEST("Test"),
        UNKNOWN("UNKNOWN");
        ;

        public final String value;
    }
}
