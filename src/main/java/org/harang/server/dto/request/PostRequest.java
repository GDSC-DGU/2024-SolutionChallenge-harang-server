package org.harang.server.dto.request;

import jakarta.validation.constraints.NotNull;
import org.harang.server.domain.enums.Gender;
import org.harang.server.domain.enums.Status;

import java.time.LocalDateTime;

public record PostRequest(
        @NotNull LocalDateTime createdAt,
        @NotNull String title,
        @NotNull String content,
        @NotNull String chatLink,
        Gender preferredGender,
        int preferredAge,
        LocalDateTime preferredStartAt,
        LocalDateTime preferredEndAt,
        @NotNull Status status
        ) {

}
