package org.harang.server.service;

import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.harang.server.domain.*;
import org.harang.server.domain.enums.Status;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.request.PostRequest;
import org.harang.server.dto.response.PostResponse;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.harang.server.repository.*;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final WaitingRepository waitingRepository;

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

        Location savedLocation = locationRepository.save(
                Location.builder()
                        .post(savedPost)
                        .name(request.preferredLocation())
                        .xValue(request.x())
                        .yValue(request.y())
                        .build()
        );

        for (String categoryName : request.categoryList()) {
            Category category = categoryRepository.findByName(categoryName);
            if (category != null) {
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

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());
    }

    // TODO: 이전 호출 결과도 중복되어 반환되는 문제 원인 파
    public List<PostResponse> getSearchResults(String title) {
        return postRepository.findByTitleIsContaining(title)
                .stream()
                .map(p -> PostResponse.of(p))
                .toList();
    }

    @Transactional
    public void deletePost(Long memberId, Long postId) {
        Post post = postRepository.findByIdOrThrow(postId);

        locationRepository.deleteByPostId(postId);
        postRepository.delete(post);
    }

    public List<PostResponse> getRecommendedPosts() {
        List<Post> posts = postRepository.findAll();

        // 전체 게시글이 3개 미만이면 빈 리스트 반환 (추천 게시글 렌더 X)
        if (posts.size() < 3) {
            return new ArrayList<>();
        }

        Collections.shuffle(posts);
        List<Post> recommendedPosts = posts.subList(0, 3);

        return recommendedPosts
                .stream()
                .map(p -> PostResponse.of(p))
                .toList();
    }

    public List<PostResponse> getChattings(Long memberId) {
        // 사용자가 물뿌리개인 경우 - 매칭 대기 리스트의 게시글 조회
        // 사용자가 새싹인 경우 - 자신이 작성한 게시글 조회

        Member member = memberRepository.findByIdOrThrow(memberId);
        Type type = member.getType();
        List<Post> posts;

        if (type.equals(Type.SPROUT)) {
            // 자신이 작성한 게시글 조회
            posts = postRepository.findAllByMemberId(memberId);
        } else if ((type.equals(Type.WATERING))) {
            // 자신이 매칭 대기 중인 게시글 조회
            List<Waiting> myWaitings = waitingRepository.findAllByMemberId(memberId);
            posts = myWaitings.stream().map(w -> w.getPost()).toList();
        } else {
            throw new CustomException(ErrorMessage.INVALID_MEMBER_TYPE);
        }

        return posts.stream().map(p -> PostResponse.of(p)).toList();
    }
}
