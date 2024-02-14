package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.harang.server.domain.enums.Gender;
import org.harang.server.domain.enums.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age", nullable = false)
    private Long age;

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "member")
    private List<Matching> matchingList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @Builder
    public Member(String name, Type type, Gender gender, Long age) {
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.age = age;
    }
}
