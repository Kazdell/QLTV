package com.practice.QLTV.repository;

import com.practice.QLTV.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateRepository extends JpaRepository<InvalidatedToken, String> {
}
