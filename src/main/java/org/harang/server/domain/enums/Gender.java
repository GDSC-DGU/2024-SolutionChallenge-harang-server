package org.harang.server.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    NONE("none");

    private final String value;
}
