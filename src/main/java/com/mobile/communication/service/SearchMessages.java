package com.mobile.communication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class SearchMessages {

    @Value("${address.file}")
    private String addressFile;

    public void searchingMessages(String date){

        String formated = MessageFormat.format(addressFile, date);

        System.out.println("bla : " + formated);
    }


}
