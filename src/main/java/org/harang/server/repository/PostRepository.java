package org.harang.server.repository;

import org.harang.server.domain.Post;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorMessage.POST_NOT_FOUND));
    }
}