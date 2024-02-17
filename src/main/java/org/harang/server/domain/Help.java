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
@Table(name = "help")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Help {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "help")
    private List<MemberHelp> memberHelpList = new ArrayList<>();

    @Builder
    public Help(String name) {
        this.name = name;
    }
}
