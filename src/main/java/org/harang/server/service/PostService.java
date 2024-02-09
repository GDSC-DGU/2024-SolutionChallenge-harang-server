package org.harang.server.service;

import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Member;
import org.harang.server.domain.Post;
import org.harang.server.domain.enums.Status;
import org.harang.server.dto.request.PostRequest;
import org.harang.server.repository.MemberRepository;
import org.harang.server.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Post createPost(Long memberId, PostRequest request) {
        int birthYear = request.preferredAge();
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - birthYear;
        LocalDateTime createdAt = LocalDateTime.now();
        Status status = Status.WAITING;

        Member member = memberRepository.findByIdOrThrow(memberId);

        Post savedPost = postRepository.save(
                Post.builder()
                        .member(member)
                        .createdAt(createdAt)
                        .title(request.title())
                        .content(request.content())
                        .chatLink(request.chatLink())
                        .preferredGender(request.preferredGender())
                        .preferredAge(age)
                        .preferredStartAt(request.preferredStartAt())
                        .preferredEndAt(request.preferredEndAt())
                        .status(status)
                        .build()
        );
        return savedPost;
    }

}
