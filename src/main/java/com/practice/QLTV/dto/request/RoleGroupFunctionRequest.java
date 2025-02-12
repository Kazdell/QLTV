package com.practice.QLTV.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleGroupFunctionRequest {
    Integer roleGroupId;
    Integer functionId;
}
