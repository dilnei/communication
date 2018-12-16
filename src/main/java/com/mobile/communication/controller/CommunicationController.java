package com.mobile.communication.controller;

import com.mobile.communication.domain.Metric;
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

    @RequestMapping(path = "/retrieveInformation",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get retrieve information related with certain Mobile Communication Platform")
    public ResponseEntity<Message> getInformation(
            @ApiParam(name = "receivedDate", value = "It receives a date parameter (YYYYMMDD)", required = true)
            @RequestParam(value = "receivedDate", required = true) String receivedDate) throws Exception{

        searchService.search(receivedDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message());
    }

    @RequestMapping(path = "/metrics",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "It returns a set of counters related with the processed JSON file")
    public ResponseEntity<Metric> getMetrics() throws Exception{

        return ResponseEntity.status(HttpStatus.CREATED).body(new Metric());
    }
}
