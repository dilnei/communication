package com.mobile.communication.service;

import com.mobile.communication.domain.Kpi;
import com.mobile.communication.repository.KpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KPIService {

    @Autowired
    private KpiRepository kpiRepository;

    public Kpi getCounters() {
        return kpiRepository.findById(1L).get();
    }

}
