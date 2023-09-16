package com.shortLinks.telegramBot.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveToShortLinkTest {

    @Test
    public void openWebpages() {
        String url = "wwwyaru";
        Assertions.assertEquals(false, MoveToShortLink.openWebpages(url));
    }
}
