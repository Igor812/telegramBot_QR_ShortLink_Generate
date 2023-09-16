package com.shortLinks.telegramBot.exceptions;

import java.io.IOException;

public class FileSendingException extends IOException {
    public FileSendingException(String message) {
        super(message);
    }
}
