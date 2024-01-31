package org.harang.server.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {

    // ok - 200
    OK("20001", HttpStatus.OK, "성공"),

    // created - 201
    CREATED("20101", HttpStatus.CREATED, "생성 완료"),

    // accepted - 202
    ACCEPTED("20201", HttpStatus.ACCEPTED, "수락 완료"),
    ;

    private String code;
    @JsonIgnore
    private HttpStatus status;
    private String message;

}
