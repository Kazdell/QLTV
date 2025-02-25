package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.entity.Function;
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
    public FunctionDTO createFunction(FunctionDTO functionDTO) {
        Function function = Function.builder()
                .functionCode(functionDTO.getFunctionCode())
                .functionName(functionDTO.getFunctionName())
                .description(functionDTO.getDescription())
                .build();
        function = functionRepository.save(function);
        return new FunctionDTO(function.getId(), function.getFunctionCode(), function.getFunctionName(), function.getDescription());
    }

    @Override
    public List<FunctionDTO> getAllFunctions() {
        return functionRepository.findAll().stream()
                .map(function -> new FunctionDTO(function.getId(), function.getFunctionCode(), function.getFunctionName(), function.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public FunctionDTO getFunctionByCode(String functionCode) {
        Function function = functionRepository.findByFunctionCode(functionCode)
                .orElseThrow(() -> new RuntimeException("Function not found"));
        return mapToDTO(function);
    }

    private FunctionDTO mapToDTO(Function function) {
        return new FunctionDTO(
                function.getId(),
                function.getFunctionCode(),
                function.getFunctionName(),
                function.getDescription()
        );
    }
}
