package com.shortLinks.telegramBot.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinksExTest {
    @Test
    public void getShortLink(){
        LinksEx linksEx = new LinksEx();
        String expected = "Str";
        Assertions.assertEquals(expected,linksEx.getShortLink(expected));
    }
}
