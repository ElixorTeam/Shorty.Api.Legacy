package ru.shorty.linkshortener.exceptions.common;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Object fieldValue) {
        super(String.format("User not found with UID : '%s'", fieldValue));
    }
}
