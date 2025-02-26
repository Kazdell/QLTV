package com.practice.QLTV.service;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;

import java.util.List;

public interface FunctionService {
    ApiResponse<FunctionDTO> createFunction(FunctionDTO functionDTO);
    ApiResponse<List<FunctionDTO>> getAllFunctions();
    ApiResponse<FunctionDTO> getFunctionByCode(String functionCode);
}