package com.shortLinks.telegramBot.interfaces;

import com.shortLinks.telegramBot.exceptions.FileSendingException;

public interface SenderServiceImpl {
    String sendShortLink(long chatId, String shortLink);
    void sendImage(Long chatId) throws FileSendingException;
    void sendQRImage(Long chatId) throws FileSendingException;
}
