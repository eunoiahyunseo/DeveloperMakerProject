package com.hyunseo.programming.dmaker.repository;

import com.hyunseo.programming.dmaker.entity.Developer;
import com.hyunseo.programming.dmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository
        extends JpaRepository<RetiredDeveloper, Long> {
}
