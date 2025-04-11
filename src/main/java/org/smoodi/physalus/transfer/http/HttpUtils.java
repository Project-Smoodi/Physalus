package org.smoodi.physalus.transfer.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    public static boolean isError(HttpStatus status) {
        return isClientError(status) || isServerError(status);
    }

    public static boolean isClientError(HttpStatus status) {
        return status.status >= 400 && status.status < 500;
    }

    public static boolean isServerError(HttpStatus status) {
        return status.status >= 500 && status.status < 600;
    }

    public static boolean isSuccess(HttpStatus status) {
        return status.status >= 200 && status.status < 300;
    }
}
