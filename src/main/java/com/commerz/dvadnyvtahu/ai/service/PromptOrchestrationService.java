package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.client.dto.ChatMessage;
import com.commerz.dvadnyvtahu.ai.client.midjourney.ImageRequest;
import com.commerz.dvadnyvtahu.ai.client.midjourney.Response;
import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.WordUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

@Service
public class PromptOrchestrationService {

    private Logger logger = LoggerFactory.getLogger(PromptOrchestrationService.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private MidJourneyService midJourneyService;

    @Value("${output.file}")
    private String outputFile;

    private static final String STYLE = "In a very photorealistic style";
    private static final String FLAGS = "--stylize 1 --iw 1.2 --no building --no wall --no pillar --no column --no floor";

    private static final String CHATGPT_INSTRUCTION_TEMPLATE =
            "Develop a brief six-line description of a %s scenery portraying " + // scenery
                    "a %s adult person being in location %s." + // gender  //location
                    "Emphasize the %s aspect in the scenery." + //location
                    "Refer to the person just as person. The language used should be simple with short sentences, " +
                    "similar to a reading-level vocabulary.";

    @SneakyThrows
    public void generateImage(String photoUrl, String gender, String location, String rawPrompt) {
        String instruction = String.format(CHATGPT_INSTRUCTION_TEMPLATE, rawPrompt, gender, location , location);
        ChatMessage chatGptResponse = chatService.prompt(instruction);
        String rawMjPrompt = chatGptResponse.getContent();
        String rawMjPromptJoined;

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(rawMjPrompt)) {
            try (Scanner scanner = new Scanner(rawMjPrompt)) {
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                }
            }
        }
        rawMjPromptJoined = sb.toString().replace("\"", "").replace(".,", ". ");
        String finalPromptForMJ = String.format("%s %s %s::100 %s::300 %s",
                photoUrl,
                STYLE,
                rawMjPromptJoined,
                rawPrompt,
                FLAGS);

        logger.info("MidJourney request part url {}'.", photoUrl);
        logger.info("MidJourney request part style {}'.", STYLE);
        logger.info("MidJourney request part chatGpt composition {}'.", WordUtils.wrap(rawMjPromptJoined, 150));
        logger.info("MidJourney request part enhanced activity prompt {}'.", rawPrompt);
        logger.info("MidJourney request full {}'.", WordUtils.wrap(finalPromptForMJ, 150));

        ImageRequest ir = new ImageRequest();
        ir.setMsg(finalPromptForMJ);

        Response mjResponse = midJourneyService.callMidJourney(ir);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss");
        String strDate = dateFormat.format(date);


        String filename = String.format("collabothon_output_%s", strDate);
        downloadFile(mjResponse.getImageUrl(), filename);
        filename = String.format("collabothon_output_1_%s", strDate);
        downloadFile(mjResponse.getImageUrls().get(0), filename);
        filename = String.format("collabothon_output_2_%s", strDate);
        downloadFile(mjResponse.getImageUrls().get(1), filename);
        filename = String.format("collabothon_output_3_%s", strDate);
        downloadFile(mjResponse.getImageUrls().get(2), filename);
        filename = String.format("collabothon_output_4_%s", strDate);
        downloadFile(mjResponse.getImageUrls().get(3), filename);
    }

    private void downloadFile(String fromUrl, String filename) {
        try {
            String filePathFull = String.format(this.outputFile, filename);
            File outputFile = new File(filePathFull);

            HttpGet httpGet = new HttpGet(fromUrl);
            try (CloseableHttpClient httpclient = HttpClients.createDefault();
                 CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                // From apache-common-io
                FileUtils.copyInputStreamToFile(entity1.getContent(), outputFile);
            }
            logger.info("File downloaded {}.", filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
