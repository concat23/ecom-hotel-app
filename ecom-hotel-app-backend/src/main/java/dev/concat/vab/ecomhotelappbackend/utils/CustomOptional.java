package dev.concat.vab.ecomhotelappbackend.utils;

import java.util.NoSuchElementException;

public class CustomOptional<T>{
    private final T value;

    private CustomOptional(T value) {
        this.value = value;
    }

    public static <T> CustomOptional<T> ofNullable(T value) {
        return new CustomOptional<>(value);
    }

    public boolean isEmpty() {
        return value == null;
    }

    public T get() {
        if (isEmpty()) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

}
