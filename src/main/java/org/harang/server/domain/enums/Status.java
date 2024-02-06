package org.harang.server.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {
    WAITING("waiting"),
    CHATTING("chatting"),
    MATCHING("matching"),
    FINISH("finish");

    private final String value;
}
