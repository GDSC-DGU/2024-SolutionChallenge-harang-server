package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.harang.server.domain.enums.MGender;
import org.harang.server.domain.enums.PStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "chat_link", nullable = false)
    private String chatLink;

    @Column(name = "preferred_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private MGender preferredGender;

    @Column(name = "preferred_age", nullable = false)
    private Long preferredAge;

    @Column(name = "preferred_start_at", nullable = false)
    private LocalDateTime preferredStartAt;

    @Column(name = "preferred_end_at", nullable = false)
    private LocalDateTime preferredEndAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PStatus status = PStatus.WAITING;

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "post")
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Matching> matchingList = new ArrayList<>();

    @Builder
    public Post(Member member, LocalDateTime createdAt, String title, String content, String chatLink, MGender preferredGender, Long preferredAge, LocalDateTime preferredStartAt, LocalDateTime preferredEndAt, PStatus status) {
        this.member = member;
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.chatLink = chatLink;
        this.preferredGender = preferredGender;
        this.preferredAge = preferredAge;
        this.preferredStartAt = preferredStartAt;
        this.preferredEndAt = preferredEndAt;
        this.status = status;
    }
}
