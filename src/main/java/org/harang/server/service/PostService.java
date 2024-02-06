package org.harang.server.service;

import lombok.RequiredArgsConstructor;
import org.harang.server.domain.Post;
import org.harang.server.dto.request.PostRequest;
import org.harang.server.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequest request) {
        int birthYear = request.preferredAge();
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - birthYear;

        Post savedPost = postRepository.save(
                Post.builder()
                        .createdAt(request.createdAt())
                        .title(request.title())
                        .content(request.content())
                        .chatLink(request.chatLink())
                        .preferredGender(request.preferredGender())
                        .preferredAge(age)
                        .preferredStartAt(request.preferredStartAt())
                        .preferredEndAt(request.preferredEndAt())
                        .status(request.status())
                        .build()
        );
        return savedPost;
    }
}
