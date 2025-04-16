package org.smoodi.physalus.status;

public interface Stated {

    State getState();

    default boolean is(State value, State... value2) {
        for (State state : value2) {
            if (state.equals(value)) {
                return true;
            }
        }

        return false;
    }

    enum State {

        // Lifecycle
        NONE,
        SETTING,
        INITIALIZING,
        STARTING,
        RUNNING,
        STOPPING,
        STOPPED,
        DEAD,
        WAITING,
        SLEEPING,
        ERRORED,

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
