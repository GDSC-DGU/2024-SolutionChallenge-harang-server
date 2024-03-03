package org.harang.server.repository;

import java.util.List;
import org.harang.server.domain.Member;
import org.harang.server.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    List<Waiting> findAllByPostId(Long postId);

    List<Waiting> findAllByMemberId(Long memberId);
}
