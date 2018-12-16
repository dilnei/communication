package com.mobile.communication.exception;

public interface BusinessException {
    String FIND_ADRRESS = "Setting URI Address for Messages({0})";
    String READING_MESSAGES = "Reading messages...";
    String MESSAGES_READ = "{0} messages read";
    String CLOSING_STREAMS = "Closing streams...";
    String ERROR_FIELDS = "{0} rows with fields errors";
    String PARSE_ISSUE = "Problem parsing data, possible JSON malformation {0}";
}
