package com.mobile.communication.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private Long callsNumberOrigin;
    private Long callsNumberDestination;

    public Call() {
    }

    public Call(String code, Long callsNumberOrigin, Long callsNumberDestination) {
        this.code = code;
        this.callsNumberOrigin = callsNumberOrigin;
        this.callsNumberDestination = callsNumberDestination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
