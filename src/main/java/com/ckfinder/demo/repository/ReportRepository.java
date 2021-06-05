package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
}
