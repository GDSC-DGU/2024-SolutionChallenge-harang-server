package org.harang.server.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.harang.server.annotation.MemberId;
import org.harang.server.dto.common.ApiResponse;
import org.harang.server.dto.request.MatchingRequest;
import org.harang.server.dto.type.SuccessMessage;
import org.harang.server.service.MatchingService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/matchings")
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;

    @PostMapping
    public ApiResponse<?> createMatching(@MemberId Long memberId,
                                         final @Valid @RequestBody MatchingRequest matchingRequest) {
        matchingService.createMatching(memberId, matchingRequest);
        return ApiResponse.success(SuccessMessage.OK);
    }

    @PatchMapping("/{postId}/end")
    /*
    매칭 관련 api는 matchings로 시작, matching controller에 묶이는게 좋을 것 같아서
    matchings 다음에 postId(매칭 데이터를 식별하는 데에 사용)가 오게 되었는데
    더 좋은 엔드포인트가 있다면 알려주세요
    */
    public ApiResponse<?> finishMatching(final @NotNull @PathVariable("postId") Long postId) {
        matchingService.finishMatching(postId);
        return ApiResponse.success(SuccessMessage.OK);
    }
}
