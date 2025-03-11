package org.smoodi.physalus.engine.port;

import lombok.Getter;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.Tagged;
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
            String tag
    ) {
        this(portNumber, Collections.singletonList(tag));
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

        var matchedStandardPorts = Arrays.stream(StandardPorts.values()).filter(it -> it.portNumber == portNumber).map(it -> it.name).toList();

        if (!matchedStandardPorts.isEmpty()) {
            return matchedStandardPorts;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Port port) {
            return portNumber == port.portNumber;
        }
        if (obj instanceof SocketListeningPort port) {
            return portNumber == port.getPortNumber();
        }
        return false;
    }
}
