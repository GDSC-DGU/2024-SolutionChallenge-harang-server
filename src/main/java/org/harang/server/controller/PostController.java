package org.harang.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Post;
import org.harang.server.dto.common.ApiResponse;
import org.harang.server.dto.request.PostRequest;
import org.harang.server.dto.type.SuccessMessage;
import org.harang.server.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<?> createPost(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody PostRequest request) {
        Long memberId = extractMemberIdFromAccessToken(accessToken);

        Post post = postService.createPost(memberId, request);

        return ApiResponse.success(SuccessMessage.CREATED);
    }

    public Long extractMemberIdFromAccessToken(String accessToken) {
        String[] parts = accessToken.split(" ");
        if (parts.length == 2) {
            String memberIdString = parts[1];
            return Long.parseLong(memberIdString);
        } else {
            throw new IllegalArgumentException("Invalid Access Token");
        }
    }
}
