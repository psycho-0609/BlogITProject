package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.ReportEntity;
import com.finnal.blogit.repository.ReportRepository;
import com.finnal.blogit.service.inter.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<ReportEntity> findAll() {
        return repository.findAll();
    }
}
