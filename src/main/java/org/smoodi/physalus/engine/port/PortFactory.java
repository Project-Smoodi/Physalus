package org.smoodi.physalus.engine.port;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smoodi.physalus.Tagged;
import org.smoodi.physalus.transfer.StandardPorts;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PortFactory {

    private static final List<Integer> HTTP_PORTS = List.of(
            StandardPorts.HTTP.portNumber,
            StandardPorts.HTTP_.portNumber,
            StandardPorts.HTTPS.portNumber);

    public static Port http(int portNumber) {
        if (!HTTP_PORTS.contains(portNumber)) {
            throw new IllegalArgumentException("Invalid HTTP port number: " + portNumber);
        }

        return HttpPort.of(new PortValue(portNumber, Tagged.StandardTags.HTTP.value));
    }
}
