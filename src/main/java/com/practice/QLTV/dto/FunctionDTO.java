package com.practice.QLTV.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunctionDTO {
    private Integer id;
    private String functionCode;
    private String functionName;
    private String description;
}
