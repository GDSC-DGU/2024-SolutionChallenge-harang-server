package org.harang.server.service;

import lombok.RequiredArgsConstructor;
import org.harang.server.domain.*;
import org.harang.server.domain.enums.Status;
import org.harang.server.dto.request.PostRequest;
import org.harang.server.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Transactional
    public Post createPost(Long memberId, PostRequest request) {
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
                        .preferredAge(request.preferredAge())
                        .preferredStartAt(request.preferredStartAt())
                        .preferredEndAt(request.preferredEndAt())
                        .status(status)
                        .build()
        );

        for (String categoryName: request.categoryList()) {
            Category category = categoryRepository.findByName(categoryName);
            if(category != null) {
                PostCategory savedPostCategory =
                        PostCategory.builder()
                                .post(savedPost)
                                .category(category)
                                .build();
                postCategoryRepository.save(savedPostCategory);
            }
        }
        return savedPost;
    }

}
