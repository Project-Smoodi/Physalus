package org.smoodi.physalus.engine;

import org.smoodi.physalus.Adapter;

public interface Adapted {

    boolean addAdapter(Adapter adapter);

    boolean removeAdapter(Adapter adapter);

    boolean removeAdapter(String tag);
}
