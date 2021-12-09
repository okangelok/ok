package ru.sfedu.samokat.model;

import java.util.Objects;

public class Result<T> {
    private final T object;
    private final Status status;
    private final String message;

    public Result(T object, Status status, String message) {
        this.object = object;
        this.status = status;
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(object, result.object) && status == result.status && Objects.equals(message, result.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, status, message);
    }

    @Override
    public String toString() {
        return "Result{" +
                "object=" + object +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
