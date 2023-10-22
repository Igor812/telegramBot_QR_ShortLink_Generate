package com.shortLinks.telegramBot.config;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton SHORT_LINK_BUTTON= new InlineKeyboardButton("ShortLink");
    private static final InlineKeyboardButton MOVE_TO_LINK_BUTTON = new InlineKeyboardButton("MoveToLink");
    private static final InlineKeyboardButton Get_LONG_LINK_BUTTON = new InlineKeyboardButton("GetLongLink");
    private static final InlineKeyboardButton QR_CODE_BUTTON = new InlineKeyboardButton("QR-code");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        SHORT_LINK_BUTTON.setCallbackData("/ShortLink");
        MOVE_TO_LINK_BUTTON.setCallbackData("/MoveToLink");
        Get_LONG_LINK_BUTTON.setCallbackData("/GetLongLink");
        QR_CODE_BUTTON.setCallbackData("/QR-code");
        HELP_BUTTON.setCallbackData("/help");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, SHORT_LINK_BUTTON, QR_CODE_BUTTON, HELP_BUTTON);
        List<InlineKeyboardButton> secondLine = List.of(MOVE_TO_LINK_BUTTON, Get_LONG_LINK_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline, secondLine);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
