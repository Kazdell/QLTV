package com.practice.QLTV.repository;

import com.practice.QLTV.entity.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Integer> {
    Optional<Function> findByFunctionCode(String functionCode);
}
