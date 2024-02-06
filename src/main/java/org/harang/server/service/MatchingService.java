package org.harang.server.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Member;
import org.harang.server.domain.Post;
import org.harang.server.dto.request.MatchingRequest;
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

        matchingRepository.save(matchingRequest.toEntity(post, member));
    }
}
