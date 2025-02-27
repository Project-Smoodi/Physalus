package org.smoodi.physalus;

import lombok.Getter;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.exchange.StandardPorts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class Port implements Tagged {

    private final int portNumber;

    private final List<String> tag;

    public Port(
            int portNumber
    ) {
        if (portNumber < 0 || portNumber > 65535) {
            throw new IllegalArgumentException("Port number out of range");
        }
        this.portNumber = portNumber;
        this.tag = autoTagging(portNumber);
    }

    public Port(
            int portNumber,
            List<String> tag
    ) {
        if (portNumber < 0 || portNumber > 65535) {
            throw new IllegalArgumentException("Port number out of range");
        }
        this.portNumber = portNumber;
        this.tag = tag;
    }

    @UnmodifiableArray
    private static List<String> autoTagging(int portNumber) {

        var matchedStandardPorts = Arrays.stream(StandardPorts.values()).filter(it -> it.portNumber == portNumber).map(Enum::name).toList();

        if (!matchedStandardPorts.isEmpty()) {
            return matchedStandardPorts;
        } else {
            return Collections.emptyList();
        }
    }
}
