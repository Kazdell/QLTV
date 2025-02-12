package com.practice.QLTV.repository;

import com.practice.QLTV.entity.RoleGroupFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleGroupFunctionRepository extends JpaRepository<RoleGroupFunction, Integer> {
    List<RoleGroupFunction> findByRoleGroupId(Integer roleGroupId);
}
