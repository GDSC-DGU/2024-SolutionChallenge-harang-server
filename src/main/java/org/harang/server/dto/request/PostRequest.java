package org.harang.server.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.harang.server.domain.enums.Gender;
import org.harang.server.domain.enums.Status;

import java.time.LocalDateTime;

public record PostRequest(
        @JsonProperty("createdAt") @NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
        @JsonProperty("title") @NotNull @Size(min = 10, max = 24 ) String title,
        @JsonProperty("content") @NotNull(message = "내용을 입력해주세요.") String content,
        @JsonProperty("chatLink") @NotNull(message = "오픈채팅방 링크를 첨부해주세요.") String chatLink,
        @JsonProperty("preferredGender") Gender preferredGender,
        @JsonProperty("preferredAge") int preferredAge,
        @JsonProperty("preferredStartAt") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime preferredStartAt,
        @JsonProperty("preferredEndAt") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime preferredEndAt,
        @JsonProperty("status") @NotNull Status status
        ) {

}
