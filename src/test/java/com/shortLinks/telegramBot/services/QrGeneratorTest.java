package com.shortLinks.telegramBot.services;


import net.glxn.qrgen.javase.QRCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class QrGeneratorTest {

    @Test
    public void fileCreater()  {
        QrGenerator qrGenerator = new QrGenerator();
        ByteArrayOutputStream stream = QRCode
                .from("image")
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        qrGenerator.fileCreater(bis);
        String expected = "image.jpg";
        boolean flag = false;
        File file = new File("src/main/java/com/shortLinks/telegramBot/temp/"+expected);
        if(file.exists()) flag=true;
        Assertions.assertEquals(true,flag);
        file.delete();
    }
}
