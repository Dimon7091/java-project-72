package hexlet.code.utils;

import lombok.Getter;

@Getter

public class Result<T> {
    private final boolean success;
    private final T data;
    private final String message;

    private Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, data, message);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, null, message);
    }
}
