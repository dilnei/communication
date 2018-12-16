package com.mobile.communication.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Metric {

    /**
     * Metric's identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Long wrongFields;

    /**
     * Number of calls origin/destination grouped by country code (https://en.wikipedia.org/wiki/MSISDN).
     */
    private Long callNumbers;

    /**
     * Relationship between OK/KO calls.
     */
    private Long relationship;

    /**
     * Average call duration grouped by country code (https://en.wikipedia.org/wiki/MSISDN).
     */
    private Long callDuration;

    /**
     * Word occurrence ranking for the given words in message_content field.
     */
    private Long wordOccurrence;


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
        return wrongFields;
    }

    public void setWrongFields(Long wrongFields) {
        this.wrongFields = wrongFields;
    }

    public Long getCallNumbers() {
        return callNumbers;
    }

    public void setCallNumbers(Long callNumbers) {
        this.callNumbers = callNumbers;
    }

    public Long getRelationship() {
        return relationship;
    }

    public void setRelationship(Long relationship) {
        this.relationship = relationship;
    }

    public Long getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Long callDuration) {
        this.callDuration = callDuration;
    }

    public Long getWordOccurrence() {
        return wordOccurrence;
    }

    public void setWordOccurrence(Long wordOccurrence) {
        this.wordOccurrence = wordOccurrence;
    }
}
