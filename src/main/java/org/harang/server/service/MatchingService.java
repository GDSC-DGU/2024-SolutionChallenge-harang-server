package org.harang.server.service;

import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Matching;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
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

    @Transactional
    public void finishMatching(Long postId) {
        Post post = postRepository.findByIdOrThrow(postId);
        // 게시글에 대해 생성된 매칭 조회
        Matching matching = matchingRepository.findByPostIdOrThrow(postId);

        // 게시글이 매칭 상태가 아니라면 예외 발생
        if (!post.getStatus().equals(Status.MATCHING)) {
            throw new CustomException(ErrorMessage.POST_NOT_MATCHED);
        }
        // 매칭이 이미 종료된 상태라면 예외 발생
        if (matching.isDone()) {
            throw new CustomException(ErrorMessage.MATCHING_ALREADY_DONE);
        }

        // 게시글 상태를 finish로, 매칭 종료 여부를 true로 변경
        post.updateStatus(Status.FINISH);
        matching.updateDoneTrue();

        // 변경사항 갱신
        postRepository.save(post);
        matchingRepository.save(matching);
    }
}
