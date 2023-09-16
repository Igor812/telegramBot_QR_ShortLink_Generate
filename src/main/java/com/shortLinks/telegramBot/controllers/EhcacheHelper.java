package com.shortLinks.telegramBot.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Data
@Slf4j
@Controller
@NoArgsConstructor
public class EhcacheHelper {
    @Value("${server.url}")
    String port;

    public void init() {
        cacheManager = CacheManager.getInstance();
        cache = cacheManager.getCache("cacheStore");
    }

    private static CacheManager cacheManager = CacheManager.getInstance();
    private Cache cache = cacheManager.getCache("cacheStore");

    public void addBufferLink(String shortLink, String longLink) {
        if (shortLink.contains(port)) shortLink = shortLink.replace(port, "");
        cache.put(new Element(shortLink, longLink));
    }

    public String getBufferLink(String shortLink) {
        if (!cache.isKeyInCache(shortLink)) {
            log.warn("Запрошенная ссылка отсутствует " + shortLink);
            return "Запрошенная ссылка отсутствует";
        }
        Element element = cache.get(shortLink);
        String string = (String) element.getObjectValue();
        return string;
    }


    public static void closeEhcache() {
        cacheManager.shutdown();
        log.info("Cache is close");
    }
}
