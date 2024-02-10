package org.harang.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.harang.server.annotation.MemberId;
import org.harang.server.dto.common.ApiResponse;
import org.harang.server.dto.request.MatchingRequest;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.dto.type.SuccessMessage;
import org.harang.server.service.MatchingService;
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
    public ApiResponse<?> createMatching(final @Valid @RequestBody MatchingRequest matchingRequest) {
        matchingService.createMatching(matchingRequest);
        return ApiResponse.success(SuccessMessage.OK);
    }
}
