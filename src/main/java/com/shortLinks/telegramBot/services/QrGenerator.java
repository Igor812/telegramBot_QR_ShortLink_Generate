package com.shortLinks.telegramBot.services;

import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class QrGenerator {

    @Value("${image.path}")
    String path;

    public void generateQRCodeImage(String barcodeText) {
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        fileCreater(bis);
    }

    public void fileCreater(ByteArrayInputStream bis){
        try {
            File outputfile = new File(path);
            ImageIO.write(ImageIO.read(bis), "jpg", outputfile);
        }
        catch (Exception exception) { exception.printStackTrace();}
    }

}
