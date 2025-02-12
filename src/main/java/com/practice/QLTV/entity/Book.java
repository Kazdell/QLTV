package com.practice.QLTV.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "author", nullable = false)
    String author;

    @Column(name = "total_quantity", nullable = false)
    Integer totalQuantity;

    @Column(name = "borrow_quantity", nullable = false)
    Integer borrowQuantity = 0;

    @Column(name = "is_active", nullable = false)
    Boolean isActive = true;

    @Column(name = "is_deleted", nullable = false)
    Boolean isDeleted = false;
}
