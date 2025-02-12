package com.practice.QLTV.dto;

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
    private Integer roleGroupId;
    private Integer functionId;
}
