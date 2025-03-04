package org.smoodi.physalus.status;

public interface Stated {

    Value state = Value.NONE;

    enum Value {

        // Lifecycle
        NONE,
        SETTING,
        INITIALIZING,
        STARTING,
        RUNNING,
        STOPPING,
        DEAD,
        WAITING,
        SLEEPING,

        // Async
        SYNCHRONIZING,
        SPENDING,
        BUFFERING,
        CACHING,

        // Other
        ALPHA,
        BRAVO,
        CHARLIE,
        DELTA,
        ECHO,
        FOXTROT,
        GOLF,
        HOTEL,
        INDIA,
        JULIETT,
        KILO,
        LIMA,
        MIKE,
        NOVEMBER,
        OSCAR,
        PAPA,
        QUEBEC,
        ROMEO,
        SIERRA,
        TANGO,
        UNIFORM,
        VICTOR,
        WHISKY,
        XRAY,
        YANKEE,
        ZULU
    }
}
