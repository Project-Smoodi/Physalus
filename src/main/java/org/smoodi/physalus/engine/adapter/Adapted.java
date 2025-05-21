package org.smoodi.physalus.engine.adapter;

import org.smoodi.annotation.NotNull;

public interface Adapted {

    boolean addAdapter(@NotNull Adapter adapter);

    boolean removeAdapter(@NotNull Adapter adapter);

    boolean removeAdapter(@NotNull String tag);
}
