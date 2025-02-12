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
@Table(name = "role_group_function")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleGroupFunction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "role_group_id", nullable = false)
    Integer roleGroupId;

    @Column(name = "function_id", nullable = false)
    Integer functionId;
}
