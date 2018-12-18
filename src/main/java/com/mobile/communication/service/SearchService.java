package com.mobile.communication.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.communication.domain.*;
import com.mobile.communication.domain.enums.MessageType;
import com.mobile.communication.domain.enums.MobileSubscriber;
import com.mobile.communication.domain.enums.Word;
import com.mobile.communication.exception.BusinessException;
import com.mobile.communication.repository.MetricRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SearchService {

    static transient Logger logger = Logger.getLogger(SearchService.class.getName());

    @Value("${address.file}")
    private String addressFile;

    @Autowired
    private MetricRepository metricRepository;

    public void search(String date) throws Exception {

        URI uri = new URI(MessageFormat.format(addressFile, date));
        logger.log(Level.INFO, BusinessException.FIND_ADRRESS, uri.toString());

        StringReader reader = new StringReader(IOUtils.toString(uri, Charset.forName("UTF-8")));
        logger.log(Level.INFO, BusinessException.READING_MESSAGES);

        BufferedReader buffer = new BufferedReader(reader);
        Supplier<Stream<String>> stream = () -> buffer.lines();

        List<Message> messages = buffer.lines().map(item -> parse(item))
                .collect(Collectors.toList());
        logger.log(Level.INFO, BusinessException.MESSAGES_READ, messages.size());

        Long errorFields = stream.get().map(item -> parse(item))
                .filter(Objects::isNull).count();
        logger.log(Level.INFO, BusinessException.ERROR_FIELDS, errorFields);

        reader.close();
        buffer.close();
        logger.log(Level.INFO, BusinessException.CLOSING_STREAMS);


        Long lost = missingFields(messages);
        logger.log(Level.INFO, BusinessException.MISSING_FIELDS, lost);

        Long blank = blankFields(messages);
        logger.log(Level.INFO, BusinessException.BLANK_CONTENT, blank);


        List<Call> calls = callsOriginDestination(messages);

        List<AverageCall> averageCalls = averageCallsDuration(messages);

        List<Ranking> rankingsWords = rankingWordsOccurrence(messages);

        Metric metric = new Metric();
        metric.setMissingFields(lost);
        metric.setBlankFields(blank);
        metric.setWrongFields(errorFields);
        metric.setCalls(calls);
        metric.setRelationship(null);
        metric.setAverageCallDuration(averageCalls);
        metric.setWordOccurrence(rankingsWords);

        metricRepository.save(metric);

//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(metric);
//        logger.log(Level.INFO, jsonInString);

    }

    /**
     * Word occurrence ranking for the given words in message_content field.
     *
     * @param messages
     * @return List<Ranking>
     */
    private List<Ranking> rankingWordsOccurrence(List<Message> messages) {
        List<Ranking> rankings = new ArrayList<>();
        Map<String, List<Message>> rankingWords = messages.stream()
                .filter(msg -> msg.getMessage_type() != null && msg.getMessage_type().equals(MessageType.MSG.name()) && msg.getMessage_content() != null && !msg.getMessage_content().isEmpty() && msg.getMessage_content().contains(Word.ARE.name())
                        || (msg.getMessage_type() != null && msg.getMessage_type().equals(MessageType.MSG.name()) && msg.getMessage_content() != null && !msg.getMessage_content().isEmpty() && msg.getMessage_content().contains(Word.FINE.name()))
                        || (msg.getMessage_type() != null && msg.getMessage_type().equals(MessageType.MSG.name()) && msg.getMessage_content() != null && !msg.getMessage_content().isEmpty() && msg.getMessage_content().contains(Word.HELLO.name()))
                        || (msg.getMessage_type() != null && msg.getMessage_type().equals(MessageType.MSG.name()) && msg.getMessage_content() != null && !msg.getMessage_content().isEmpty() && msg.getMessage_content().contains(Word.NOT.name()))
                        || (msg.getMessage_type() != null && msg.getMessage_type().equals(MessageType.MSG.name()) && msg.getMessage_content() != null && !msg.getMessage_content().isEmpty() && msg.getMessage_content().contains(Word.YOU.name()))
                )
                .collect(Collectors.groupingBy(Message::getMessage_content));

        rankingWords.forEach((key, value) -> {
            Ranking ranking = new Ranking();
            ranking.setQuantity(value.size());
            if (key.contains(Word.ARE.name()) && key.contains(Word.YOU.name())) {
                Ranking rankingAreYou = new Ranking();
                rankingAreYou.setWord(Word.ARE);
                ranking.setWord(Word.YOU);
            } else if (key.contains(Word.NOT.name())) {
                ranking.setWord(Word.NOT);
            } else if (key.contains(Word.HELLO.name())) {
                ranking.setWord(Word.HELLO);
            } else if (key.contains(Word.FINE.name())) {
                ranking.setWord(Word.FINE);
            } else if (key.contains(Word.ARE.name()) && !key.contains(Word.YOU.name())) {
                ranking.setWord(Word.ARE);
            } else if (key.contains(Word.YOU.name()) && !key.contains(Word.ARE.name())) {
                ranking.setWord(Word.YOU);
            }
            rankings.add(ranking);
        });
        return rankings;
    }

    /**
     * Average call duration grouped by country code (https://en.wikipedia.org/wiki/MSISDN).
     *
     * @param messages
     * @return List<AverageCall>
     */
    private List<AverageCall> averageCallsDuration(List<Message> messages) {
        List<AverageCall> averageCalls = new ArrayList<>();
        Map<String, Double> averageMessages = messages.stream()
                .filter(msg -> msg.getMessage_type().equals(MessageType.CALL.name()) && msg.getDuration() != null)
                .collect(Collectors.groupingBy(Message::getOrigin, Collectors.averagingInt(Message::getDuration)));

        averageMessages.forEach((key, value) -> {
            AverageCall averageCall = new AverageCall();
            averageCall.setCountryCode(String.valueOf(MobileSubscriber.convertCodeToMobileSubscriber(key).getCountryAsCode()));
            averageCall.setAverageCallDuration(value.intValue());
            averageCalls.add(averageCall);
        });
        return averageCalls;
    }

    /**
     * Number of calls origin/destination grouped by country code (https://en.wikipedia.org/wiki/MSISDN).
     *
     * @param messages
     * @return List<Call>
     */
    private List<Call> callsOriginDestination(List<Message> messages) {
        List<Call> calls = new ArrayList<>();
        Map<String, Map<String, List<Message>>> OriginDestination = messages.stream()
                .filter(msg -> msg.getMessage_type().equals(MessageType.CALL.name()))
                .collect(Collectors.groupingBy(Message::getOrigin, Collectors.groupingBy(Message::getDestination)));

        OriginDestination.forEach((key, value) -> {
            Call call = new Call();
            call.setCallsNumberOrigin(messages.stream().filter(k -> k.getOrigin().equals(key)
                    && k.getMessage_type().equals(MessageType.CALL.name())).count());
            call.setCallsNumberDestination(value.values().stream().count());
            call.setCountryCode(String.valueOf(MobileSubscriber.convertCodeToMobileSubscriber(key).getCountryAsCode()));
            calls.add(call);
        });
        return calls;
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
                        || item.getDuration() != null && item.getDuration().toString().isEmpty()
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
        } catch (JsonParseException | JsonMappingException e) {
            logger.log(Level.WARNING, BusinessException.PARSE_ISSUE);
        } catch (IOException e) {
            logger.log(Level.WARNING, BusinessException.PARSE_ISSUE);
        }
        return message;
    }
}
