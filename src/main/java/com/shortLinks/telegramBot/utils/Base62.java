package com.shortLinks.telegramBot.utils;
import com.shortLinks.telegramBot.exceptions.Base62Exception;

public class Base62 {
    static private final int BASE = 62;
    static private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static private final String MAX_INTEGER;

    static {
        try {
            MAX_INTEGER = to(Integer.MAX_VALUE);
        } catch (Base62Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String to(int x) throws Base62Exception {
        if (x < 0) {
            String msg = String.format("x must not be negative, have '%d'", x);
            throw new Base62Exception(msg);
        }
        if (x == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        while (x != 0) {
            result.append(ALPHABET.charAt(x % BASE));
            x /= BASE;
        }
        return result.reverse().toString();
    }
}
