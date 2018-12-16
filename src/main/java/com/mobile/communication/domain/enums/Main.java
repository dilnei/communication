package com.mobile.communication.domain.enums;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.communication.domain.Message;
import com.mobile.communication.domain.Metric;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static String TESTE = "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732100,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 180,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732100,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 180,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732100,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 180,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732101,\"origin\": 49151123456,\"destination\": 49151123456,\"duration\": 10,\"status_code\": \"KO\",\"status_description\": \"301: Self calling\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732102,\"origin\": 49151123456,\"destination\": 49151123456,\"duration\": 10,\"status_code\": \"KO\",\"status_description\": \"301: Self calling\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732103,\"origin\": 49151123456,\"destination\": 49151123456,\"duration\": 10,\"status_code\": \"KO\",\"status_description\": \"301: Self calling\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732110,\"origin\": 49151123456,\"destination\": 49151123789,\"duration\": 30,\"status_code\": \"OK\",\"status_description\": \"\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732140,\"origin\": 49151123456,\"destination\": 49151123789,\"duration\": 40,\"status_code\": \"OK\",\"status_description\": \"\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732180,\"origin\": 49151123456,\"destination\": 49151123789,\"duration\": 50,\"status_code\": \"OK\",\"status_description\": \"\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 447005963734,\"destination\": 447005963437,\"duration\": 10,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645720,\"origin\": 447005963734,\"destination\": 447005963437,\"duration\": 10,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645740,\"origin\": 447005963734,\"destination\": 447005963437,\"duration\": 10,\"status_code\": \"KO\",\"status_description\": \"302: Poor signal\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645760,\"origin\": 447005963734,\"destination\": 447005963437,\"duration\": 10,\"status_code\": \"KO\",\"status_description\": \"302: Poor signal\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645780,\"origin\": 447005963437,\"destination\": 447005963734,\"duration\": 10,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645800,\"origin\": 447005963437,\"destination\": 447005963734,\"duration\": 10,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": \"\",\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"K.O.\",\"status_description\": \"\"}\n" +
            "{\"message_type\": \"\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"K.O.\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": \"\",\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": \"\",\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": \"\",\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559300,\"origin\": 34960000001,\"destination\": 34960000101,\"message_content\": \"1. HELLO\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559300,\"origin\": 34960000002,\"destination\": 34960000102,\"message_content\": \"1. HELLO\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559300,\"origin\": 34960000003,\"destination\": 34960000103,\"message_content\": \"1. HELLO\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559360,\"origin\": 49151123456,\"destination\": 49151123789,\"message_content\": \"1. HELLO\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559420,\"origin\": 49151123456,\"destination\": 49151123789,\"message_content\": \"\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559480,\"origin\": 49151123456,\"destination\": 49151123789,\"message_content\": \"\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559540,\"origin\": 49151123456,\"destination\": 49151123789,\"message_content\": \"\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559600,\"origin\": 49151123456,\"destination\": 49151123789,\"message_content\": \"5. GOOD\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559360,\"origin\": 447005963437,\"destination\": 447005963734,\"message_content\": \"1. HELLO\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559370,\"origin\": 447005963734,\"destination\": 447005963437,\"message_content\": \"1.1 HI\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559380,\"origin\": 447005963437,\"destination\": 447005963734,\"message_content\": \"2. HOW ARE YOU?\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559330,\"origin\": 34960000001,\"destination\": 34960000101,\"message_content\": \"1.1 HI\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559331,\"origin\": 34960000002,\"destination\": 34960000102,\"message_content\": \"\",\"message_status\": \"DELIVERED\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559332,\"origin\": 34960000003,\"destination\": 34960000103,\"message_content\": \"1.1 HI\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559390,\"origin\": 447005963734,\"destination\": 447005963437,\"message_content\": \"2.1 FINE\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559400,\"origin\": 447005963437,\"destination\": 447005963734,\"message_content\": \"3. LUNCH TODAY?\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517559410,\"origin\": 447005963734,\"destination\": 447005963437,\"message_content\": \"3.1 SURE\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517562900,\"origin\": 34960000001,\"destination\": 34960000001,\"message_content\": \"1. HELLO\",\"message_status\": \"DELI\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517562910,\"origin\": 34960000002,\"destination\": 34960000102,\"message_content\": \"1. HELLO\",\"message_status\": \"\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517562920,\"origin\": 34960000003,\"destination\": 34960000103,\"message_content\": \"1. HELLO\",\"message_status\": \"SEEN\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517562960,\"origin\": \"\",\"destination\": 34960000001,\"message_content\": \"1. HELLO\",\"message_status\": \"DELI\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517563020,\"origin\": 34960000001,\"destination\": \"\",\"message_content\": \"1. HELLO\",\"message_status\": \"DELI\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": \"\",\"origin\": 34960000001,\"destination\": 34960000001,\"message_content\": \"1. HELLO\",\"message_status\": \"DELI\"}\n" +
            "{\"message_type\": \"MSG\",\"timestamp\": 1517563140,\"origin\": 34960000002,\"destination\": 34960000102,\"message_content\": \"1. HELLO\",\"message_status\": \"SEEN\"}";


    static String TESTE2 = "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 120,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"NULL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"status_code\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517645700,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 121,\"status_code\": OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732100,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 122,\"status\": \"OK\",\"status_description\": \"OK\"}\n" +
            "{\"message_type\": \"CALL\",\"timestamp\": 1517732100,\"origin\": 34969000001,\"destination\": 34969000101,\"duration\": 123,\"status_code\": \"OK\",\"status_description\": \"OK\"\n" +
            "{}";

    public static void main(String args[]) throws Exception {

        StringReader reader = new StringReader(TESTE2);
        BufferedReader buffer = new BufferedReader(reader);

//        List<String> messagesStr = buffer
//                .lines()
//                .filter(line -> !line.startsWith("{") && !line.endsWith("}"))
//                .collect(Collectors.toList());

//        messagesStr.stream().forEach(System.out::println);

        Long messagesStrem = buffer
                .lines()
                .map(item -> parse(item))
                .filter(Objects::isNull).count();
        System.out.println("Number of Objects: " + messagesStrem );

        List<Message> messages = buffer
                .lines()
                .map(item -> parse(item))
                .collect(Collectors.toList());

        reader.close();
        buffer.close();

        Metric metric = new Metric();

        missingFields(messages);
        System.out.println("Number of rows with missing fields: " );

//        metric.setMissingFields(n);


    }


    private static void missingFields(List<Message> messages) {
        messages.stream()
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
                .forEach(item -> {
                    System.out.println(item.toString());
                });
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

//    private static Message valid(String item) {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        try {
//            JsonNode jsonNode = mapper.readTree(item);
//            jsonNode.size();
//
//        } catch (JsonProcessingException e) {
//            throw new UncheckedIOException(e);
//        }
//    }

}
