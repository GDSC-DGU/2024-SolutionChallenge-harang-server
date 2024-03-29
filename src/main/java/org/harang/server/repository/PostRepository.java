package org.harang.server.repository;

import java.util.List;
import org.harang.server.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorMessage.POST_NOT_FOUND));
    }

    List<Post> findByTitleIsContaining(String title);

    List<Post> findAllByMemberId(Long memberId);
}
