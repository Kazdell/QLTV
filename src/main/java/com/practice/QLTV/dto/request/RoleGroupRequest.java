package com.practice.QLTV.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroupRequest {
    @NotBlank(message = "Role group code is mandatory")
    private String roleGroupCode;

    @NotBlank(message = "Role group name is mandatory")
    private String roleGroupName;

    private String description;
}
