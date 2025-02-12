package com.practice.QLTV.repository;

import com.practice.QLTV.entity.Function;
import com.practice.QLTV.entity.RoleGroupFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleGroupFunctionRepository extends JpaRepository<RoleGroupFunction, Integer> {
    List<RoleGroupFunction> findByRoleGroupId(Integer roleGroupId);
    @Query("SELECT f FROM Function f " +
            "INNER JOIN RoleGroupFunction rgf ON rgf.functionId = f.id " +
            "WHERE rgf.roleGroupId = :roleGroupId")
    List<Function> findFunctionsByRoleGroup(@Param("roleGroupId") Integer roleGroupId);
}
