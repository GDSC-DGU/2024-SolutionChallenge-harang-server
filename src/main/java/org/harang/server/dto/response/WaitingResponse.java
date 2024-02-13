package org.harang.server.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WaitingResponse {
    private Long memberId;
    private String nickname;

    @Builder
    public WaitingResponse(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
