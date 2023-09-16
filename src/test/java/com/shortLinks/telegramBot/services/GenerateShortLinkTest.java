package com.shortLinks.telegramBot.services;

import com.shortLinks.telegramBot.exceptions.Base62Exception;
import com.shortLinks.telegramBot.utils.Base62;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class GenerateShortLinkTest {

    @Test
    public void generateShortLink() throws Base62Exception {
        String port = "http://localhost:8081/";
        String inputString = "www.ya.ru";
        String expected = "http://localhost:8081/CpRe2";
        Assertions.assertEquals(expected, port+Base62.to(Math.abs(inputString.hashCode())));
    }
}
