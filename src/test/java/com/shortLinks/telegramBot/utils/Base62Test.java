package com.shortLinks.telegramBot.utils;

import com.shortLinks.telegramBot.exceptions.Base62Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base62Test {
    @Test
    public void to() throws Base62Exception {
        String expected = "CpRe2";
        Assertions.assertEquals(expected,Base62.to(Math.abs("www.ya.ru".hashCode())));
    }
}
