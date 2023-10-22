package com.shortLinks.telegramBot.services;

import com.shortLinks.telegramBot.exceptions.Base62Exception;
import com.shortLinks.telegramBot.interfaces.GenerateServiceImpl;
import com.shortLinks.telegramBot.utils.Base62;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerateShortLink implements GenerateServiceImpl {
    @Value ("${server.url}")
    String port;

    @Override
    public String generateShortLink(String link) {
        String shortLink = null;
        try {
            if(link.hashCode()<0) {
                String subres = 1+String.valueOf(link.hashCode());
                shortLink =Base62.to(Integer.parseInt(subres));
            } else {
                String subres = 0+String.valueOf(link.hashCode());
                shortLink =Base62.to(Integer.parseInt(subres));
            }
        } catch (Base62Exception e) {
            e.getStackTrace();
        }
        return port+shortLink;
    }

}
