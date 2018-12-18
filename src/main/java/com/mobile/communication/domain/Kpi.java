package com.mobile.communication.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "KPI")
public class Kpi {

    /**
     * Metric's identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    /**
     * Total number of processed JSON files.
     */
    @Column(name = "NUMBERFILES")
    private Integer numberFiles;

    /**
     * Total number of rows.
     */
    @Column(name = "ROWSNUMBER")
    private Integer rowsNumber;

    /**
     * Total number of calls.
     */
    @Column(name = "CALLSNUMBER")
    private Integer callsNumber;

    /**
     * Total number of messages.
     */
    @Column(name = "MESSAGESNUMBER")
    private Integer messagesNumber;

    /**
     * Total number of different origin country codes (https://en.wikipedia.org/wiki/MSISDN).
     */
    @Column(name = "DIFFERENTORIGINCOUNTRY")
    private Integer differentOriginCountry;

    /**
     * Total number of different destination country codes (https://en.wikipedia.org/wiki/MSISDN).
     */
    @Column(name = "DIFFERENTDESTINATIONCOUNTRY")
    private Integer differentDestinationCountry;

    /**
     * Duration of each JSON process.
     */
    @Column(name = "PROCESSINGTIME")
    private Long processingTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberFiles() {
        return numberFiles;
    }

    public void setNumberFiles(Integer numberFiles) {
        this.numberFiles = numberFiles;
    }

    public Integer getRowsNumber() {
        return rowsNumber;
    }

    public void setRowsNumber(Integer rowsNumber) {
        this.rowsNumber = rowsNumber;
    }

    public Integer getCallsNumber() {
        return callsNumber;
    }

    public void setCallsNumber(Integer callsNumber) {
        this.callsNumber = callsNumber;
    }

    public Integer getMessagesNumber() {
        return messagesNumber;
    }

    public void setMessagesNumber(Integer messagesNumber) {
        this.messagesNumber = messagesNumber;
    }

    public Integer getDifferentOriginCountry() {
        return differentOriginCountry;
    }

    public void setDifferentOriginCountry(Integer differentOriginCountry) {
        this.differentOriginCountry = differentOriginCountry;
    }

    public Integer getDifferentDestinationCountry() {
        return differentDestinationCountry;
    }

    public void setDifferentDestinationCountry(Integer differentDestinationCountry) {
        this.differentDestinationCountry = differentDestinationCountry;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }
}
