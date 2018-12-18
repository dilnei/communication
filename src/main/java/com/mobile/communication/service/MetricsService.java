package com.mobile.communication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.communication.domain.Message;
import com.mobile.communication.domain.Metric;
import com.mobile.communication.repository.MetricRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricsService {

    @Autowired
    private MetricRepository metricRepository;

    public Metric getCounters(){
      return metricRepository.findTopByOrderByIdDesc();
    }

}
