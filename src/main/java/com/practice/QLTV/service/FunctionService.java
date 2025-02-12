package com.practice.QLTV.service;

import com.practice.QLTV.dto.FunctionDTO;
import java.util.List;

public interface FunctionService {
    FunctionDTO createFunction(FunctionDTO functionDTO);
    List<FunctionDTO> getAllFunctions();
}
