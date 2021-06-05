package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.ReportEntity;
import com.ckfinder.demo.repository.ReportRepository;
import com.ckfinder.demo.service.inter.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService implements IReportService {

    @Autowired
    private ReportRepository repository;

    @Override
    public ReportEntity save(ReportEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ReportEntity> findById(Integer id) {
        return repository.findById(id);
    }
}
