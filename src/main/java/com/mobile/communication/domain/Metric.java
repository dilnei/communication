package com.mobile.communication.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Metric {

    /**
     * Metric's identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    /**
     * Number of rows with missing fields.
     */
    private Long missingFields;

    /**
     * Number of messages with blank content.
     */
    private Long blankFields;

    /**
     * Number of rows with fields errors.
     */
    private Long errorFields;

    /**
     * Number of calls origin/destination grouped by country code (https://en.wikipedia.org/wiki/MSISDN).
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Call> calls;

    /**
     * Relationship between OK/KO calls.
     */
    private Long relationship;

    /**
     * Average call duration grouped by country code (https://en.wikipedia.org/wiki/MSISDN).
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<AverageCall> averageCallDuration;

    /**
     * Word occurrence ranking for the given words in message_content field.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Ranking> wordOccurrence;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(Long missingFields) {
        this.missingFields = missingFields;
    }

    public Long getBlankFields() {
        return blankFields;
    }

    public void setBlankFields(Long blankFields) {
        this.blankFields = blankFields;
    }

    public Long getWrongFields() {
        return errorFields;
    }

    public void setWrongFields(Long wrongFields) {
        this.errorFields = wrongFields;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public void setCalls(List<Call> calls) {
        this.calls = calls;
    }

    public Long getRelationship() {
        return relationship;
    }

    public void setRelationship(Long relationship) {
        this.relationship = relationship;
    }


    public List<AverageCall> getAverageCallDuration() {
        return averageCallDuration;
    }

    public void setAverageCallDuration(List<AverageCall> averageCallDuration) {
        this.averageCallDuration = averageCallDuration;
    }

    public List<Ranking> getWordOccurrence() {
        return wordOccurrence;
    }

    public void setWordOccurrence(List<Ranking> wordOccurrence) {
        this.wordOccurrence = wordOccurrence;
    }
}
