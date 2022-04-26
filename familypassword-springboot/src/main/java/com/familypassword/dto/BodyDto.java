package com.familypassword.dto;

import lombok.Data;

@Data
public class BodyDto<T> {

    private final String message;
    private T object;

    private BodyDto(String message, T object) {
        this.message = message;
        this.object = object;
    }

    public static <T> BodyDto<T> of(String message, T object) {
        return new BodyDto<>(message, object);
    }
}
