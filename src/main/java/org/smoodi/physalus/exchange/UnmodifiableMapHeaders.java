package org.smoodi.physalus.exchange;

import lombok.NoArgsConstructor;
import org.smoodi.annotation.NotNull;
import org.smoodi.annotation.Overload;
import org.smoodi.annotation.StaticFactoryMethod;
import org.smoodi.annotation.array.EmptyArray;
import org.smoodi.annotation.array.EmptyableArray;

import java.lang.instrument.UnmodifiableModuleException;
import java.util.Map;

/**
 * <p><b>Unmodifiable</b> wrapper class of headers saved as {@link Map Map}.</p>
 *
 * @author Daybreak312
 * @see MapHeaders
 * @since v0.0.1
 */
@NoArgsConstructor
public class UnmodifiableMapHeaders extends MapHeaders {

    public UnmodifiableMapHeaders(@NotNull final Map<String, String> headers) {
        super(headers);
    }

    public UnmodifiableMapHeaders(@NotNull final Headers headers) {
        super(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static UnmodifiableMapHeaders of(@NotNull final Map<String, String> headers) {
        assert headers != null;
        return new UnmodifiableMapHeaders(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static UnmodifiableMapHeaders of(@NotNull final Headers headers) {
        return new UnmodifiableMapHeaders(headers);
    }

    @EmptyArray
    @NotNull
    @StaticFactoryMethod
    public static UnmodifiableMapHeaders empty() {
        return new UnmodifiableMapHeaders();
    }

    @Override
    public void set(String key, String value) {
        throw new UnmodifiableModuleException();
    }

    @Override
    public void remove(String key) {
        throw new UnmodifiableModuleException();
    }

    @Override
    public void clear() {
        throw new UnmodifiableModuleException();
    }
}
