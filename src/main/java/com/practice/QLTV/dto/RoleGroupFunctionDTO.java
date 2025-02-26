package com.practice.QLTV.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleGroupFunctionDTO {
    private Integer id;

    @NotNull(message = "Role group ID cannot be null")
    private Integer roleGroupId;

    @NotNull(message = "Function ID cannot be null")
    private Integer functionId;
}