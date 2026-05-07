package com.factorysuite.repository;

import com.factorysuite.entity.ProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessFlowRepository extends JpaRepository<ProcessEntity, Integer> {




}
