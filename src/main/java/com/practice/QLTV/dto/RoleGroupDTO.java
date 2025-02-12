package com.practice.QLTV.dto;

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
    private String roleGroupCode;
    private String roleGroupName;
    private String description;
}

