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
@Table(name = "role_group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "role_group_code", nullable = false, unique = true)
    String roleGroupCode;

    @Column(name = "role_group_name", nullable = false)
    String roleGroupName;

    @Column(name = "description")
    String description;
}

