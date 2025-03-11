package org.smoodi.physalus.engine.port;

import lombok.Getter;
import org.smoodi.annotation.array.UnmodifiableArray;
import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.exchange.StandardPorts;

import java.util.Arrays;

@Getter
public class Port implements Tagged {

    private final int portNumber;

    private final String tag;

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
        if (portNumber < 0 || portNumber > 65535) {
            throw new IllegalArgumentException("Port number out of range");
        }
        this.portNumber = portNumber;
        this.tag = tag;
    }

    @UnmodifiableArray
    private static String autoTagging(int portNumber) {

        var matchedStandardPorts = Arrays.stream(StandardPorts.values()).filter(it -> it.portNumber == portNumber).map(it -> it.name).toList();

        if (matchedStandardPorts.size() == 1) {
            return matchedStandardPorts.getFirst();
        } else {
            return StandardTags.UNKNOWN.value;
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
