package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.harang.server.domain.enums.Gender;
import org.harang.server.domain.enums.Status;

import java.time.LocalDate;
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

    @Column(name = "preferred_gender")
    @Enumerated(EnumType.STRING)
    private Gender preferredGender;

    @Column(name = "preferred_age")
    private int preferredAge;

    @Column(name = "preferred_start_at")
    private LocalDate preferredStartAt;

    @Column(name = "preferred_end_at")
    private LocalDate preferredEndAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Matching> matchingList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostCategory> postCategoryList = new ArrayList<>();

    @Builder
    public Post(Member member, LocalDateTime createdAt, String title, String content, String chatLink, Gender preferredGender, int preferredAge, LocalDate preferredStartAt, LocalDate preferredEndAt, Status status) {
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

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }
}
