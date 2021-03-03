package com.railways.login.dto;

import lombok.Getter;

@Getter
public enum UserValidation {

    SUCCESS("Login success!"),
    USER_NOT_FOUND("Can't find user"),
    WRONG_PASSWORD("You entered wrong message");

    private String message;

    UserValidation(String message) {
        this.message = message;
    }
}
