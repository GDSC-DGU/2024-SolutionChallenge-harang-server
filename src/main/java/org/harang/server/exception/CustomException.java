package org.harang.server.exception;

import lombok.Getter;
import org.harang.server.dto.type.ErrorMessage;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}
