package com.mobile.communication.domain;

import java.util.Date;

public class Message {


    // The type of the message. Two values are valid: {CALL|MSG}
    private String message_type;

    // The timestamp of the message.
    private Date timestamp;

    // Mobile identifier of the origin mobile (MSISDN)
    private String origin;

    // Mobile identifier of the destination mobile (MSISDN)
    private String destination;

    // Call duration. Only for CALL (message_type)
    private String duration;

    // Status code of the call. Only for CALL (message_type). Two values are valid: {OK|KO}
    private String status_code;

    // Status description of the call. Only for CALL (message_type)
    private String status_description;

    // Content of the message. Only for MSG (message_type)
    private String message_content;

    // Status of the message. Two values are valid: {DELIVERED|SEEN}
    private String message_status;

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_status() {
        return message_status;
    }

    public void setMessage_status(String message_status) {
        this.message_status = message_status;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Message message = (Message) object;
        return timestamp.equals(message.timestamp);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), timestamp);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Message{" +
                "message_type='" + message_type + '\'' +
                ", timestamp=" + timestamp +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", duration='" + duration + '\'' +
                ", status_code='" + status_code + '\'' +
                ", status_description='" + status_description + '\'' +
                ", message_content='" + message_content + '\'' +
                ", message_status='" + message_status + '\'' +
                '}';
    }
}