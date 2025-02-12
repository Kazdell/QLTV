package com.practice.QLTV.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroupResponse {
    private String roleGroupCode;
    private String roleGroupName;
    private String description;
}
