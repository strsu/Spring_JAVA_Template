package com.example.template.domain.Product.entity;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many = Item, One = Member - 한명의 유저는 여러개의 물품을 올릴 수 있다.
    @JoinColumn
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ProductCategory productCategory;

    @Column(length = 64, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = true)
    private long price; // 가격

    @Column(columnDefinition = "boolean default false")
    private boolean isNego; // 가격협상가능여부

    @Column
    private Point point;
}
