package com.commerz.dvadnyvtahu.ai.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ImageEditService {

    @SneakyThrows
    public void addTextToImage(String url, String text) {
        HttpURLConnection connection =  (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        BufferedImage image = ImageIO.read(connection.getInputStream());
        connection.disconnect();

        Font font = new Font("Times New Roman", Font.BOLD, 40);

        Graphics g = image.getGraphics();
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(text, 0, 20);

        File outputFile = new File("C:\\temp\\img1.jpg");

        ImageIO.write(image, "jpg", outputFile);

        int i = 1;


//        ImagePlus image = IJ.openImage(path);
//
//        Font font = new Font("Arial", Font.BOLD, 18);
//
//        ImageProcessor ip = image.getProcessor();
//        ip.setColor(Color.GREEN);
//        ip.setFont(font);
//        ip.drawString(text, 0, 20);

    }

}
