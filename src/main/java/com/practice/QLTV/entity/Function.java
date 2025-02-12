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
@Table(name = "`function`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Function extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "function_code", nullable = false, unique = true)
    String functionCode;

    @Column(name = "function_name", nullable = false)
    String functionName;

    String description;
}

