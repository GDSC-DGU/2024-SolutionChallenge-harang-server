package org.harang.server.controller;

import lombok.RequiredArgsConstructor;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.response.JwtTokenResponse;
import org.harang.server.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/jwt")
@RequiredArgsConstructor
public class MockJwtController {
    // TODO: 로그인 구현 후 삭제

    private final JwtUtil jwtUtil;

    @GetMapping
    public JwtTokenResponse getToken(){
        return jwtUtil.generateTokens(Long.valueOf(1), Type.SPROUT);
    }
}
