package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.ReadLaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadLaterRepository extends JpaRepository<ReadLaterEntity,Long> {
}
