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
public class FunctionDTO {
    private Integer id;

    @NotBlank(message = "Function code cannot be blank")
    private String functionCode;

    @NotBlank(message = "Function name cannot be blank")
    private String functionName;

    private String description;
}