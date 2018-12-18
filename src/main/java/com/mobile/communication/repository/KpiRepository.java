package com.mobile.communication.repository;

import com.mobile.communication.domain.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiRepository extends JpaRepository<Kpi, Long> {

}