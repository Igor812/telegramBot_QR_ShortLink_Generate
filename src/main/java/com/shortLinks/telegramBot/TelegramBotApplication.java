package com.shortLinks.telegramBot;


import com.shortLinks.telegramBot.controllers.EhcacheHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                EhcacheHelper.closeEhcache();
            }
        }, "Shutdown-thread"));
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
