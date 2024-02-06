package org.harang.server.dto.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    // bad request - 400
    BAD_REQUEST("40001", HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다."),

    // unauthorized - 403
    UNAUTHORIZED("40301", HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),

    // forbidden - 403
    FORBIDDEN("40302", HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // not found - 404
    NOT_FOUND("40401", HttpStatus.NOT_FOUND, "리소스가 존재하지 않습니다."),
    MEMBER_NOT_FOUND("40402", HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    POST_NOT_FOUND("40403", HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),

    // method not allowed - 409
    METHOD_NOT_ALLOWED("40901", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메소드입니다."),

    // internal server error - 500
    INTERNAL_SERVER_ERROR("50001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    ;

    private String code;
    @JsonIgnore
    private HttpStatus status;
    private String message;

}
