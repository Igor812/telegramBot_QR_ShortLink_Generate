package com.shortLinks.telegramBot.services;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class MoveToShortLink {

    public static boolean openWebpages(String url) {
        try {
            System.setProperty("java.awt.headless", "false");
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
