package com.practice.security.exceptions;

import com.practice.security.constants.Error;
import lombok.Getter;

@Getter
public class AppException extends Exception {
    private final Error error;

    public AppException(Error error) {
        super();
        this.error = error;
    }
}
