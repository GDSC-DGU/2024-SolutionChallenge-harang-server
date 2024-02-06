package org.harang.server.dto.request;

import jakarta.validation.constraints.NotNull;
import org.harang.server.domain.Matching;
import org.harang.server.domain.Member;
import org.harang.server.domain.Post;

public record MatchingRequest(@NotNull Long postId, @NotNull Long matchedMemberId) {

    public Matching toEntity(Post post, Member member) {
        return Matching.builder()
                .post(post)
                .member(member)
                .build();
    }
}
