package com.mobile.communication.domain;

public class Country {

    private String name;
    private Integer code;
    private String prefix;
    private Double averageCallDuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Double getAverageCallDuration() {
        return averageCallDuration;
    }

    public void setAverageCallDuration(Double averageCallDuration) {
        this.averageCallDuration = averageCallDuration;
    }
}
