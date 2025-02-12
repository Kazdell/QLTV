package com.practice.QLTV.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String username;
    String fullName;
    String phoneNumber;
    String identityNumber;
    Date dob;
    String address;
    String email;
    Boolean isActive;
    Boolean isDeleted;
    Integer roleGroupId;
}
