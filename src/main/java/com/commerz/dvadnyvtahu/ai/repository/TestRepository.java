package com.commerz.dvadnyvtahu.ai.repository;

import com.commerz.dvadnyvtahu.ai.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
