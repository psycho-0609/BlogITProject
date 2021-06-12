package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.ReportEntity;

import java.util.List;
import java.util.Optional;

public interface IReportService {

    ReportEntity save(ReportEntity entity);
    void delete(Integer id);
    Optional<ReportEntity> findById(Integer id);
    List<ReportEntity> findAll();
}
