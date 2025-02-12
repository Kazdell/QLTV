package com.practice.QLTV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;

    @Column(name = "identity_number", nullable = false, unique = true)
    String identityNumber;

    @Column(name = "dob", nullable = false)
    Date dob;

    @Column(name = "address")
    String address;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "is_active", nullable = false)
    Boolean isActive = true;

    @Column(name = "is_deleted", nullable = false)
    Boolean isDeleted = false;

    @Column(name = "role_group_id", nullable = false)
    Integer roleGroupId;
}
