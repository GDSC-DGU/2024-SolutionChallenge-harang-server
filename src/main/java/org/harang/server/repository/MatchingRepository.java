package org.harang.server.repository;

import java.util.Optional;
import org.harang.server.domain.Matching;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    default Matching findByPostIdOrThrow(Long postId) {
        return findByPostId(postId).orElseThrow(() -> new CustomException(ErrorMessage.MATCHING_NOT_FOUND));
    }

    Optional<Matching> findByPostId(Long postId);
}
