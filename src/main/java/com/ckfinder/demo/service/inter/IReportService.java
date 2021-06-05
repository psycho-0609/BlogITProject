package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.ReportEntity;

import java.util.Optional;

public interface IReportService {

    ReportEntity save(ReportEntity entity);
    void delete(Integer id);
    Optional<ReportEntity> findById(Integer id);
}
