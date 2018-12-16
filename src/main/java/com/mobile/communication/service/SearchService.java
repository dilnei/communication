package com.mobile.communication.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.communication.domain.Message;
import com.mobile.communication.domain.Metric;
import com.mobile.communication.domain.enums.MessageType;
import com.mobile.communication.exception.BusinessException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SearchService {

    static transient Logger logger = Logger.getLogger(SearchService.class.getName());

    @Value("${address.file}")
    private String addressFile;

    public void search(String date) throws Exception {

        URI uri = new URI(MessageFormat.format(addressFile, date));
        logger.log(Level.INFO, BusinessException.FIND_ADRRESS, uri.toString());

        StringReader reader = new StringReader(IOUtils.toString(uri, Charset.forName("UTF-8")));
        logger.log(Level.INFO, BusinessException.READING_MESSAGES);

        BufferedReader buffer = new BufferedReader(reader);
        Long errorFields = buffer
                .lines()
                .map(item -> parse(item))
                .filter(Objects::isNull).count();
        logger.log(Level.INFO, BusinessException.ERROR_FIELDS, errorFields);

        List<Message> messages = buffer
                .lines()
                .map(item -> parse(item))
                .collect(Collectors.toList());
        logger.log(Level.INFO, BusinessException.MESSAGES_READ, messages.size());

        reader.close();
        buffer.close();
        logger.log(Level.INFO, BusinessException.CLOSING_STREAMS);


        Long miss = missingFields(messages);
        System.out.println("Number of rows with missing fields: " + miss);

        Long blank = blankFields(messages);
        System.out.println("Number of messages with blank content: " + blank);


        Metric metric = new Metric();
        metric.setMissingFields(miss);
        metric.setBlankFields(blank);
        metric.setWrongFields(errorFields);
        metric.setCallNumbers(null);
        metric.setRelationship(null);
        metric.setCallDuration(null);
        metric.setWordOccurrence(null);


        messages.forEach(item -> {
            System.out.println("Converted: " + item.toString());
        });

    }


    /**
     * Method that returns missing fields.
     *
     * @param messages
     * @return Long
     */
    private Long blankFields(List<Message> messages) {
        return messages.stream()
                .filter(item -> item.getMessage_type() != null && item.getMessage_type().isEmpty()
                        || item.getTimestamp() != null && item.getTimestamp().isEmpty()
                        || item.getOrigin() != null && item.getOrigin().isEmpty()
                        || item.getDestination() != null && item.getDestination().isEmpty()
                        || item.getDuration() != null && item.getDuration().isEmpty()
                        || item.getStatus_code() != null && item.getStatus_code().isEmpty()
                        || item.getStatus_description() != null && item.getStatus_description().isEmpty()
                        || item.getMessage_content() != null && item.getMessage_content().isEmpty()
                        || item.getMessage_status() != null && item.getMessage_status().isEmpty())
                .count();
    }

    /**
     * Method that returns missing fields.
     *
     * @param messages
     * @return Long
     */
    private Long missingFields(List<Message> messages) {
        return messages.stream()
                .filter(item -> item.getMessage_type() == null
                        || (item.getMessage_type() != null && item.getMessage_type().equals(MessageType.MSG.name()) && item.getTimestamp() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.MSG.name()) && item.getOrigin() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.MSG.name()) && item.getDestination() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.MSG.name()) && item.getMessage_content() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.MSG.name()) && item.getMessage_status() == null)
                        || (item.getMessage_type() != null && item.getMessage_type().equals(MessageType.CALL.name()) && item.getTimestamp() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.CALL.name()) && item.getOrigin() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.CALL.name()) && item.getDestination() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.CALL.name()) && item.getDuration() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.CALL.name()) && item.getStatus_code() == null
                        || item.getMessage_type() != null && item.getMessage_type().equals(MessageType.CALL.name()) && item.getStatus_description() == null))
                .count();
    }

    /**
     * Method that transform a JSON String in an Object.
     *
     * @param item
     * @return Message
     */
    private static Message parse(String item) {
        ObjectMapper mapper = new ObjectMapper();
        Message message = null;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            message = mapper.readValue(item, Message.class);
        } catch ( JsonParseException | JsonMappingException e) {
            logger.log(Level.WARNING, BusinessException.PARSE_ISSUE);
        } catch (IOException e) {
            logger.log(Level.WARNING, BusinessException.PARSE_ISSUE);
        }
        return message;
    }
}
