package com.practice.QLTV.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSearchingRequest {
    String username;
    String phoneNumber;
    String email;
    String roleName;
}
