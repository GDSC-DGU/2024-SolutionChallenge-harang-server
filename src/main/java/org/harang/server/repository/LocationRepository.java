package org.harang.server.repository;

import jakarta.transaction.Transactional;
import org.harang.server.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByPostId(Long PostId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Location l WHERE l.post.id = :postId")
    void deleteByPostId(Long postId);
}
