package org.harang.server.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Matching;
import org.harang.server.domain.Member;
import org.harang.server.domain.Post;
import org.harang.server.domain.Waiting;
import org.harang.server.domain.enums.Status;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.request.MatchingRequest;
import org.harang.server.dto.response.WaitingResponse;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.harang.server.repository.MatchingRepository;
import org.harang.server.repository.MemberInfoRepository;
import org.harang.server.repository.MemberRepository;
import org.harang.server.repository.PostRepository;
import org.harang.server.repository.WaitingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final WaitingRepository waitingRepository;
    private final MemberInfoRepository memberInfoRepository;

    @Transactional
    public void createMatching(Long memberId, MatchingRequest matchingRequest) {
        Long postId = matchingRequest.postId();
        Long matchedMemberId = matchingRequest.matchedMemberId();

        Member member = memberRepository.findByIdOrThrow(memberId);
        Post post = postRepository.findByIdOrThrow(postId);
        Member matchedMember = memberRepository.findByIdOrThrow(matchedMemberId);

        // 매칭을 생성하려는(도움을 구하는) 유저가 새싹이 아닌 경우 예외 발생
        if (!member.getType().equals(Type.SPROUT)) {
            throw new CustomException(ErrorMessage.ONLY_SPROUT_CAN_CREATE_MATCH);
        }

        // 매치된(도움을 주려는) 유저가 물뿌리개가 아닌인 경우 예외 발생
        if (!matchedMember.getType().equals(Type.WATERING)) {
            throw new CustomException(ErrorMessage.ONLY_WATERING_CAN_HELP_SPROUT);
        }

        // 이미 매칭 되었거나 매칭 종료 상태라면 예외 발생
        if (post.getStatus().equals(Status.MATCHING) || post.getStatus().equals(Status.FINISH)) {
            throw new CustomException(ErrorMessage.POST_ALREADY_MATCHED);
        }

        // 상태를 매칭으로 변경
        post.updateStatus(Status.MATCHING);
        postRepository.save(post);

        matchingRepository.save(matchingRequest.toEntity(post, matchedMember));
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

    public List<WaitingResponse> getWaitingList(Long postId) {
        List<Waiting> waitingList = waitingRepository.findAllByPostId(postId);

        List<WaitingResponse> waitingListResponse = waitingList.stream()
                .map(w -> WaitingResponse.builder()
                        .memberId(w.getMember().getId())
                        .nickname(memberInfoRepository.findByMemberIdOrThrow(w.getMember().getId()).getNickname())
                        .build()).toList();
        return waitingListResponse;
    }

    @Transactional
    public void createMatchingWaiting(Long memberId, Long postId) {
        Post post = postRepository.findByIdOrThrow(postId);
        Member member = memberRepository.findByIdOrThrow(memberId);

        // 이미 사용자가 채팅을 시작한 글이라면 예외 발생
        List<Long> waitingMemberIdList = waitingRepository.findAllByPostId(postId)
                .stream().map(w -> w.getMember().getId()).toList();
        for (Long m : waitingMemberIdList) {
            if (m.equals(memberId)) {
                throw new CustomException(ErrorMessage.CHAT_ALREADY_STARTED);
            }
        }

        // 사용자가 물뿌리개가 아니라면 예외 발생
        if (!member.getType().equals(Type.WATERING)) {
            throw new CustomException(ErrorMessage.ONLY_WATERING_CAN_HELP_SPROUT);
        }

        Waiting newWaiting = Waiting.builder()
                .post(post)
                .member(member)
                .build();

        waitingRepository.save(newWaiting);
    }
}
