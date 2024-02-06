package org.harang.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "location")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "x_value", nullable = false)
    private double xValue;

    @Column(name = "y_value", nullable = false)
    private double yValue;

    @Builder
    public Location(Post post, String name, double xValue, double yValue) {
        this.post = post;
        this.name = name;
        this.xValue = xValue;
        this.yValue = yValue;
    }
}
