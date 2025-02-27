package com.practice.QLTV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "borrow")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Borrow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id", nullable = false)
    Integer userId;

    @Column(name = "borrow_date", nullable = false)
    LocalDate borrowDate;

    @Column(name = "return_date")
    LocalDate returnDate;

    @Column(nullable = false)
    String status;

    @Column(name = "total_quantity", nullable = false)
    Integer totalQuantity;
}
