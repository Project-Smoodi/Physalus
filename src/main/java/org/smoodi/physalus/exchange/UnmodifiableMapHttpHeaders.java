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
 * <p><b>Unmodifiable</b> wrapper class of HTTP headers saved as {@link Map Map}.</p>
 *
 * @author Daybreak312
 * @see HttpHeaders
 * @since v0.0.1
 */
@NoArgsConstructor
public class UnmodifiableMapHttpHeaders extends MapHttpHeaders {

    public UnmodifiableMapHttpHeaders(@NotNull final Map<String, String> headers) {
        super(headers);
    }

    public UnmodifiableMapHttpHeaders(@NotNull final Headers headers) {
        super(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static UnmodifiableMapHttpHeaders of(@NotNull final Map<String, String> headers) {
        return new UnmodifiableMapHttpHeaders(headers);
    }

    @EmptyableArray
    @NotNull
    @Overload
    @StaticFactoryMethod
    public static UnmodifiableMapHttpHeaders of(@NotNull final Headers headers) {
        return new UnmodifiableMapHttpHeaders(headers);
    }

    @EmptyArray
    @NotNull
    public static UnmodifiableMapHttpHeaders empty() {
        return new UnmodifiableMapHttpHeaders();
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
