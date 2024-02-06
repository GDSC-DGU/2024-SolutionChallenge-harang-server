package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "matching")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Matching {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "done", nullable = false)
    private boolean done = false;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Builder
    public Matching(Member member, Post post, boolean done, double rate) {
        this.member = member;
        this.post = post;
        this.done = done;
        this.rate = rate;
    }
}
