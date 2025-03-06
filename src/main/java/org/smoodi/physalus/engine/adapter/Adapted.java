package org.smoodi.physalus.engine.adapter;

public interface Adapted {

    boolean addAdapter(Adapter adapter);

    boolean removeAdapter(Adapter adapter);

    boolean removeAdapter(String tag);
}
