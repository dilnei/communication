package com.mobile.communication.controller;

import com.mobile.communication.domain.Kpi;
import com.mobile.communication.domain.Metric;
import com.mobile.communication.service.MetricsService;
import com.mobile.communication.service.KPIService;
import com.mobile.communication.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mobile.communication.domain.Message;


@RestController
@RequestMapping("/communication/mobile-platform/")
@Api(tags = "Mobile Communication Platform", description = "Service which exposes an API through which we will retrieve information related with certain Mobile Communication Platform")
public class CommunicationController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private KPIService kpiService;

    /**
     * The service will have an HTTP endpoint that receives a date parameter (YYYYMMDD). This method will be requested to select the JSON file to process.
     *
     * @param receivedDate
     * @return @code (HttpStatus.CREATED)
     * @throws Exception
     */
    @RequestMapping(path = "/retrieveInformation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Get retrieve information related with certain Mobile Communication Platform")
    public ResponseEntity<Message> getInformation(
            @ApiParam(name = "receivedDate", value = "It receives a date parameter (YYYYMMDD)", required = true)
            @RequestParam(value = "receivedDate", required = true) String receivedDate) throws Exception {

        searchService.search(receivedDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message());
    }

    /**
     * The service will have an HTTP endpoint (/metrics) that returns a set of counters related with the processed JSON file.
     *
     * @return @code (HttpStatus.OK)
     * @throws Exception
     */
    @RequestMapping(path = "/metrics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "It returns a set of counters related with the processed JSON file")
    public ResponseEntity<Metric> getMetrics() throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(metricsService.getCounters());
    }

    /**
     * The service will have an HTTP endpoint (/kpis) that returns a set of counters related with the service.
     *
     * @return @code
     * @throws Exception
     */
    @RequestMapping(path = "/kpis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "It returns a set of counters related with the service")
    public ResponseEntity<Kpi> getkpis() throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(kpiService.getCounters());
    }
}
