package org.smoodi.physalus.engine.port;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.transfer.StandardPorts;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PortFactory {

    public static Port http(int portNumber) {
        if (!StandardPorts.HTTP_PORTS_VALUES.contains(portNumber)) {
            throw new IllegalArgumentException("Invalid HTTP port number: " + portNumber);
        }

        return HttpPort.of(portNumber);
    }
}
