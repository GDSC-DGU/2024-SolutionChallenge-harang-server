package org.harang.server.repository;

import org.harang.server.domain.Member;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    default Member findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
    }
}
