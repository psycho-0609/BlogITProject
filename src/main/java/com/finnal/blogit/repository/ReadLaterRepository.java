package com.finnal.blogit.repository;

import com.finnal.blogit.entity.ReadLaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadLaterRepository extends JpaRepository<ReadLaterEntity,Long> {
}
