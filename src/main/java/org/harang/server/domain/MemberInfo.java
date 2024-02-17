package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "memberInfo", cascade = CascadeType.ALL)
    private List<MemberHelp> memberHelpList = new ArrayList<>();

    @OneToMany(mappedBy = "memberInfo", cascade = CascadeType.ALL)
    private List<Certification> certificationList = new ArrayList<>();

    @Builder
    public MemberInfo(Member member, String nickname, String address, String refreshToken) {
        this.member = member;
        this.nickname = nickname;
        this.address = address;
        this.refreshToken = refreshToken;
    }
}
