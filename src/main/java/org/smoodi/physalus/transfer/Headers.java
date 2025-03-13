package org.smoodi.physalus.transfer;

import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.Overload;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.UnmodifiableArray;

import java.util.Map;

/**
 * <p>Wrapper of TCP headers saved as Key-Value {@link java.util.Collection Collection}.</p>
 *
 * <p>It must be implemented as a key-value stream: like {@link Map}.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
public interface Headers {

    @Nullable
    @Overload
    String get(@NotNull final String key);

    @NotNull
    @Overload
    String get(@NotNull final String key, @NotNull final String defaultValue);

    void set(@NotNull final String key, @Nullable final String value);

    void remove(@NotNull final String key);

    void clear();

    @EmptyableArray
    @UnmodifiableArray
    @NotNull
    Map<String, String> toMap();
}
