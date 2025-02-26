package com.practice.QLTV.repository;

import com.practice.QLTV.entity.RoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleGroupRepository extends JpaRepository<RoleGroup, Integer> {
    Optional<RoleGroup> findByRoleGroupCode(String roleGroupCode);
}
