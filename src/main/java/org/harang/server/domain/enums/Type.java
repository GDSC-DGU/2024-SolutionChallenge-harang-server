package org.harang.server.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Type {
    SPROUT("sprout"),
    WATERING("watering");

    private final String value;
}
