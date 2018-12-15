package com.mobile.communication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.communication.domain.Message;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchMessages {

    @Value("${address.file}")
    private String addressFile;

    public void search(String date) throws Exception {
        String uri = MessageFormat.format(addressFile, date);

        StringReader reader = new StringReader(IOUtils.toString(new URI(uri), Charset.forName("UTF-8")));
        BufferedReader buffer = new BufferedReader(reader);

        List<Message> messages = buffer
                .lines()
                .map(item -> accept(item))
                .collect(Collectors.toList());

        reader.close();
        buffer.close();

        messages.forEach(item -> {
            System.out.println("Converted: " + item.toString());
        });

    }

    private static Message accept(String item) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(item, Message.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
