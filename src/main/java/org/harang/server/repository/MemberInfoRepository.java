성package org.harang.server.repository;

import java.util.Optional;
import org.harang.server.domain.MemberInfo;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
    default MemberInfo findByMemberIdOrThrow(Long memberId) {
        return findByMemberId(memberId).orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_INFO_NOT_FOUND));
    }

    Optional<MemberInfo> findByMemberId(Long memberId);
}
