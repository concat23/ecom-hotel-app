package dev.concat.vab.ecomhotelappbackend.utils;

import dev.concat.vab.ecomhotelappbackend.response.EcomRoomResponse;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public <U> CustomOptional<U> map(Function<? super T, ? extends U> mapper) {
        if (isEmpty()) {
            return CustomOptional.ofNullable(null);
        }
        return CustomOptional.ofNullable(mapper.apply(value));
    }

    public ResponseEntity<CustomOptional<T>> orElseThrow(Supplier<? extends RuntimeException> exceptionSupplier) {
        if (isEmpty()) {
            throw exceptionSupplier.get();
        }
        return ResponseEntity.ok(this);
    }

}
