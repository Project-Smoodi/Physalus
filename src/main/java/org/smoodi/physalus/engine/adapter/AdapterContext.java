package org.smoodi.physalus.engine.adapter;

public interface AdapterContext extends AdapterManager {

    boolean addAdapter(Adapter adapter);

    boolean removeAdapter(Adapter adapter);

    boolean removeAdapter(String tag);
}
