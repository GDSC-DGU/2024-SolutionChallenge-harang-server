package org.harang.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.response.JwtTokenResponse;
import org.harang.server.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final JwtUtil jwtUtil;

    @GetMapping("/token/{memberId}")
    public ResponseEntity<JwtTokenResponse> getToken(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(jwtUtil.generateTokens(memberId, Type.WATERING));
    }
}
