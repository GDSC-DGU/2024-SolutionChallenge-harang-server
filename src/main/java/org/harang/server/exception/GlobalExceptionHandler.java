package org.harang.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.harang.server.dto.common.ApiResponse;
import org.harang.server.dto.type.ErrorMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<?> handleException(Exception e) {
        log.error("Handling Exception {}", e.getMessage());
        e.printStackTrace();
        return ApiResponse.fail(ErrorMessage.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ApiResponse<?> handleCustomException(CustomException e) {
        log.error("Handling Custom Exception {}", e.getErrorMessage().getMessage());
        return ApiResponse.fail(e.getErrorMessage());
    }
}
