package com.practice.security.exceptions;

import com.practice.security.constants.Error;
import com.practice.security.objects.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        Error error = exception.getError();
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(error.getCode());
        apiResponse.setMessage(error.getMessage());
        return ResponseEntity.status(error.getHttpStatusCode()).body(apiResponse);
    }
}
