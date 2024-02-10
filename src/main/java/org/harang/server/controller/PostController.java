package org.harang.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harang.server.annotation.MemberId;
import org.harang.server.domain.Post;
import org.harang.server.dto.common.ApiResponse;
import org.harang.server.dto.request.PostRequest;
import org.harang.server.dto.type.SuccessMessage;
import org.harang.server.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<?> createPost(@MemberId Long memberId, @Valid @RequestBody PostRequest request) {

        Post post = postService.createPost(memberId, request);

        return ApiResponse.success(SuccessMessage.CREATED);
    }
}
