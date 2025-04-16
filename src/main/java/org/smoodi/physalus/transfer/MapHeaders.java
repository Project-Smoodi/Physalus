package org.smoodi.physalus.transfer;

import lombok.NoArgsConstructor;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Nullable;
import org.smoodi.annotation.Overload;
import org.smoodi.annotation.StaticFactoryMethod;
import org.smoodi.annotation.array.EmptyArray;
import org.smoodi.annotation.array.EmptyableArray;
import org.smoodi.annotation.array.UnmodifiableArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>Wrapper class of TCP headers saved as {@link Map}.</p>
 *
 * @author Daybreak312
 * @since v0.0.1
 */
@NoArgsConstructor
public class MapHeaders implements Headers {

    protected final Map<String, String> headers = new HashMap<>();

    public MapHeaders(@NotNull final Map<String, String> headers) {
        Objects.requireNonNull(headers);

        this.headers.putAll(headers);
    }

    public MapHeaders(@NotNull final Headers headers) {
        Objects.requireNonNull(headers);

        this.headers.putAll(headers.toMap());
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static MapHeaders of(@NotNull final Map<String, String> headers) {
        return new MapHeaders(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static MapHeaders of(@NotNull final Headers headers) {
        return new MapHeaders(headers);
    }

    @EmptyArray
    @NotNull
    @StaticFactoryMethod
    public static MapHeaders empty() {
        return new MapHeaders();
    }

    @Nullable
    @Overload
    @Override
    public String get(@NotNull final String key) {
        Objects.requireNonNull(key);

        return headers.get(key);
    }

    @NotNull
    @Overload
    @Override
    public String get(@NotNull final String key, @NotNull final String defaultValue) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(defaultValue);

        final String value = headers.get(key);

        if (value == null) {
            set(key, defaultValue);
            return defaultValue;
        }

        return value;
    }

    @Override
    public void set(@NotNull final String key, @NotNull final String value) {
        Objects.requireNonNull(key);
        if (value == null) {
            return;
        }

        headers.put(key, value);
    }

    @Override
    public void remove(@NotNull final String key) {
        Objects.requireNonNull(key);
        headers.remove(key);
    }

    @Override
    public void clear() {
        headers.clear();
    }

    @EmptyableArray
    @UnmodifiableArray
    @NotNull
    @Override
    public Map<String, String> toMap() {
        return Map.copyOf(headers);
    }
}
