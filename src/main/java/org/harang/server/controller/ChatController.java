package org.harang.server.controller;

import lombok.RequiredArgsConstructor;
import org.harang.server.annotation.MemberId;
import org.harang.server.dto.common.ApiResponse;
import org.harang.server.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/chats")
@RequiredArgsConstructor
public class ChatController {
    // 엔드포인트 고려하여 새로운 컨트롤러 생성

    private final PostService postService;

    @GetMapping
    public ApiResponse<?> getChattings(@MemberId Long memberId) {
        // 사용자가 참여한 채팅방 목록을 조회
        return ApiResponse.success(postService.getChattings(memberId));
    }
}
