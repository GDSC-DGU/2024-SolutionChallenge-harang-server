package org.harang.server.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.dto.type.SuccessMessage;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private T data;
    private ErrorResponse error;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> fail(ErrorMessage errorMessage) {
        return new ApiResponse<>(false, ErrorResponse.of(errorMessage));
    }

    public ApiResponse(final boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ApiResponse(final boolean success, ErrorResponse error) {
        this.success = success;
        this.error = error;
    }
}
