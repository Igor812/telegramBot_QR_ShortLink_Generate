package com.shortLinks.telegramBot.interfaces;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "перезапуск бота"),
            new BotCommand("/ShortLink", "генерация короткой ссылки"),
            new BotCommand("/MoveToLink", "переход по ссылке"),
            new BotCommand("/GetLongLink", "поиск длинной ссылки в базе"),
            new BotCommand("/QR-code", "Генерация QR-кода"),
            new BotCommand("/help", "помощь")
    );

    String HELP_TEXT = "Данный бот позволяет генерировать короткий ссылки и QR-код для ссылки. Пользователь должен установить время работы ссылки " +
            "Доступны следующие команды:\n\n" +
            "/start - перезапуск бота\n" +
            "/ShortLink - генерация короткой ссылки\n"+
            "/MoveToLink - переход по короткой ссылке\n"+
            "/FindShortLink - поиск короткой ссылки в базе\n"+
            "/QR-code - Генерация QR-кода\n"+
            "/help - помощь";
}
