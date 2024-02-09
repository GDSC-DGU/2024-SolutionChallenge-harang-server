package org.harang.server.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Member;
import org.harang.server.domain.Post;
import org.harang.server.domain.enums.Status;
import org.harang.server.dto.request.MatchingRequest;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.harang.server.repository.MatchingRepository;
import org.harang.server.repository.MemberRepository;
import org.harang.server.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void createMatching(MatchingRequest matchingRequest) {
        Long postId = matchingRequest.postId();
        Long memberId = matchingRequest.matchedMemberId();

        Post post = postRepository.findByIdOrThrow(postId);
        Member member = memberRepository.findByIdOrThrow(memberId);

        // 이미 매칭 되었거나 매칭 종료 상태라면 예외 처리
        if (post.getStatus().equals(Status.MATCHING) || post.getStatus().equals(Status.FINISH)) {
            throw new CustomException(ErrorMessage.POST_ALREADY_MATCHED);
        }

        // 상태를 매칭으로 변경
        post.updateStatus(Status.MATCHING);
        postRepository.save(post);

        matchingRepository.save(matchingRequest.toEntity(post, member));
    }
}
