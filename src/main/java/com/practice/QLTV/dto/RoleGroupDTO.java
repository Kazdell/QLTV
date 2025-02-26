package com.practice.QLTV.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleGroupDTO {
    private Integer id;

    @NotBlank(message = "Role group code cannot be blank")
    private String roleGroupCode;

    @NotBlank(message = "Role group name cannot be blank")
    private String roleGroupName;

    private String description;
}