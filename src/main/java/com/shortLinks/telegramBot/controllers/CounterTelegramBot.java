package com.shortLinks.telegramBot.controllers;

import com.shortLinks.telegramBot.config.BotConfig;
import com.shortLinks.telegramBot.config.Buttons;
import com.shortLinks.telegramBot.exceptions.FileSendingException;
import com.shortLinks.telegramBot.interfaces.BotCommands;
import com.shortLinks.telegramBot.interfaces.SenderServiceImpl;
import com.shortLinks.telegramBot.services.GenerateShortLink;
import com.shortLinks.telegramBot.services.LinksEx;
import com.shortLinks.telegramBot.services.MoveToShortLink;
import com.shortLinks.telegramBot.services.QrGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Slf4j
@Component
public class CounterTelegramBot extends TelegramLongPollingBot implements BotCommands, SenderServiceImpl {
    final BotConfig config;
    @Value("${server.url}")
    String port;
    @Autowired
    QrGenerator qrGenerator;
    @Autowired
    GenerateShortLink generateShortLink;
    @Autowired
    LinksEx linksEx;
    @Autowired
    EhcacheHelper linkController;


    private boolean getQrCode = false;
    private boolean getShort = false;
    private boolean moveToLink = false;
    private boolean findLongLink = false;

    public CounterTelegramBot(BotConfig config) {
        this.config = config;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        linkController.init();
        long chatId = 0;
        String userName = null;
        String receivedMessage;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
                if (getQrCode) {
                    try {
                        qrGenerator.generateQRCodeImage(receivedMessage);
                        sendQRImage(chatId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    getQrCode = false;
                }
                if (getShort) {
                    try {
                        String shortLink = sendShortLink(chatId, linksEx.getShortLink(receivedMessage));
                        linkController.addBufferLink(shortLink, receivedMessage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    getShort = false;
                }
                if (moveToLink) {
                    try {
                        openWebPage(receivedMessage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    moveToLink = false;
                }
                if (findLongLink) {
                    try {
                        sendMessageText(chatId, findLink(receivedMessage));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    findLongLink = false;
                }
            }

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtils(receivedMessage, chatId, userName);
        }
    }


    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage) {
            case "/start":
                startBot(chatId, userName);
                break;
            case "/ShortLink":
                sendMessageText(chatId, "Введите ссылку для создания короткой ссылки");
                getShort = true;
                break;
            case "/MoveToLink":
                sendMessageText(chatId, "Введите ссылку для перехода");
                moveToLink = true;
                break;
            case "/GetLongLink":
                sendMessageText(chatId, "Введите ссылку для поиска: ");
                findLongLink = true;
                break;
            case "/QR-code":
                sendMessageText(chatId, "Введите ссылку для генерации QR-кода");
                getQrCode = true;
                break;
            case "/help":
                sendMessageText(chatId, HELP_TEXT);
                break;
            default:

                break;

        }

    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Привет, " + userName + "! Это Телеграмм бот для генерации коротких ссылок и QR-кодов.'");
        message.setReplyMarkup(Buttons.inlineMarkup());

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessageText(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        Update update = new Update();
        onUpdateReceived(update);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private String findLink(String url) {
        if (url.contains(linkController.getPort())) {
            url = url.replace(linkController.getPort(), "");
            linkController.getBufferLink(url);
        }
        return linkController.getBufferLink(url);
    }

    public void openWebPage(String url) {
        if (url.contains(linkController.getPort())) {
            url = url.replace(linkController.getPort(), "");
            MoveToShortLink.openWebpages(findLink(url));
        } else {
            MoveToShortLink.openWebpages(url);
        }

    }

    public String sendShortLink(long chatId, String shortLink) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Короткая ссылка: " + generateShortLink.generateShortLink(shortLink));

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        return generateShortLink.generateShortLink(shortLink);
    }

    public void sendImage(Long chatId) throws FileSendingException {
        try {
            SendPhoto photo = new SendPhoto();
            photo.setPhoto(new InputFile(new File(config.getPath())));
            photo.setChatId(chatId.toString());
            execute(photo);
        } catch (TelegramApiException e) {
            log.error(String.format("Sending image error: %s", e.getMessage()));
            throw new FileSendingException("Ошибка отправки изображения");
        }
    }

    public void sendQRImage(Long chatId) throws FileSendingException {
        sendImage(chatId);
        File file = new File(config.getPath());
        if (!file.delete()) {
            log.error(String.format("File '%s' removing error", config.getPath()));
        }
    }

}