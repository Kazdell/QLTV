package com.practice.QLTV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_like")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "user_id", nullable = false)
    Integer userId;

    @Column(name = "post_id", nullable = false)
    Integer postId;

    @Column(name = "is_like", nullable = false)
    Boolean isLike = false;
}
