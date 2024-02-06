package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member_help")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class MemberHelp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private MemberInfo memberInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "help_id")
    private Help help;

    @Builder
    public MemberHelp(MemberInfo memberInfo, Help help) {
        this.memberInfo = memberInfo;
        this.help = help;
    }
}
