package com.practice.QLTV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "borrow_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "borrow_id", nullable = false)
    Integer borrowId;

    @Column(name = "book_id", nullable = false)
    Integer bookId;

    @Column(nullable = false)
    Integer quantity;

    @Column(name = "real_return_date")
    Date realReturnDate;
}

