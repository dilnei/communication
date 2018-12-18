package com.mobile.communication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String countryCode;
    private Long callsNumberOrigin;
    private Long callsNumberDestination;

    public Call() {
    }

    public Call(String countryCode, Long callsNumberOrigin, Long callsNumberDestination) {
        this.countryCode = countryCode;
        this.callsNumberOrigin = callsNumberOrigin;
        this.callsNumberDestination = callsNumberDestination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getCallsNumberOrigin() {
        return callsNumberOrigin;
    }

    public void setCallsNumberOrigin(Long callsNumberOrigin) {
        this.callsNumberOrigin = callsNumberOrigin;
    }

    public Long getCallsNumberDestination() {
        return callsNumberDestination;
    }

    public void setCallsNumberDestination(Long callsNumberDestination) {
        this.callsNumberDestination = callsNumberDestination;
    }
}
