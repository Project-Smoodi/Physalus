package org.smoodi.physalus.engine;

import org.smoodi.physalus.status.Stated;

public interface RunningEngine
        extends Stated {

    void startEngine();

    void stopEngine();
}
