package com.mobile.communication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AverageCall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String countryCode;
    private Integer averageCallDuration;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String country) {
        this.countryCode = country;
    }

    public Integer getAverageCallDuration() {
        return averageCallDuration;
    }

    public void setAverageCallDuration(Integer averageCallDuration) {
        this.averageCallDuration = averageCallDuration;
    }
}
