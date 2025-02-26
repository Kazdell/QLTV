package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.Function;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.FunctionRepository;
import com.practice.QLTV.service.FunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepository functionRepository;

    @Override
    public ApiResponse<FunctionDTO> createFunction(FunctionDTO functionDTO) {
        if (functionRepository.existsByFunctionCode(functionDTO.getFunctionCode())) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Function code " + functionDTO.getFunctionCode() + " already exists");
        }
        Function function = Function.builder()
                .functionCode(functionDTO.getFunctionCode())
                .functionName(functionDTO.getFunctionName())
                .description(functionDTO.getDescription())
                .build();
        function = functionRepository.save(function);
        FunctionDTO result = toFunctionDTO(function);
        return ApiResponse.<FunctionDTO>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) 
                .message("Function created successfully")
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<FunctionDTO>> getAllFunctions() {
        List<FunctionDTO> functions = functionRepository.findAll().stream()
                .map(this::toFunctionDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<FunctionDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) 
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(functions)
                .build();
    }

    @Override
    public ApiResponse<FunctionDTO> getFunctionByCode(String functionCode) {
        Function function = functionRepository.findByFunctionCode(functionCode)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        FunctionDTO result = toFunctionDTO(function);
        return ApiResponse.<FunctionDTO>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) 
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(result)
                .build();
    }

    private FunctionDTO toFunctionDTO(Function function) {
        return new FunctionDTO(
                function.getId(),
                function.getFunctionCode(),
                function.getFunctionName(),
                function.getDescription()
        );
    }
}