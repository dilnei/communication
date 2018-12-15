package com.mobile.communication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mobile.communication.domain.Message;


@RestController
@RequestMapping("/communication/mobile-platform/")
@Api(tags = "Mobile Communication Platform", description = "Service which exposes an API through which we will retrieve information related with certain Mobile Communication Platform")
public class CommunicationController {

    @RequestMapping(path = "/getRetrieveInformation",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get retrieve information related with certain Mobile Communication Platform")
    public ResponseEntity<Message> getInformation(
            @ApiParam(name = "receivedDate", value = "receives a date parameter (YYYYMMDD)", required = true)
            @RequestParam(value = "receivedDate", required = true) String receivedDate) {

       return ResponseEntity.status(HttpStatus.CREATED).body(new Message());
    }

}
