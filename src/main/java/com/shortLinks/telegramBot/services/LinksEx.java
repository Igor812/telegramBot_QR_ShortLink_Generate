package com.shortLinks.telegramBot.services;

import org.springframework.stereotype.Service;

@Service
public class LinksEx {
    public String getShortLink(String receivedMessage){
        return receivedMessage;
    }
}
