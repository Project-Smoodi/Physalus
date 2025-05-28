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

        SLEEPING,

        INITIALIZING,
        INITIALIZED,

        STARTING,
        RUNNING,

        STOPPING,
        STOPPED,

        DESTROYING,
        DESTROYED,

        ERROR
    }
}
