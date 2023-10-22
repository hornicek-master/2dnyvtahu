package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.client.midjourney.*;
import com.commerz.dvadnyvtahu.ai.domain.Test;
import com.commerz.dvadnyvtahu.ai.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class MidJourneyService {
    private Logger logger = LoggerFactory.getLogger(MidJourneyService.class);

    @Autowired
    private MidJourneyClient mj;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${app.mj.token}")
    private String token;

    private String getToken() {
        return "Bearer " + token;
    }

    public Response testMj(String what) {
        ImageRequest ir = new ImageRequest();
        ir.setMsg("https://uloz.to/quickDownload/KkpbZu96Flmx "+what);
        Message m = mj.executePrompt(getToken(), ir);
        return executePrompt(m);
    }


    public Response callMidJourney(ImageRequest ir) {
        Message m = mj.executePrompt(getToken(), ir);
        return executePrompt(m);
    }

    private Response executePrompt(Message m) {
        MidResponse midResponse = null;
        while (midResponse==null || midResponse.getProgress() == null || midResponse.getProgress()<100) {
            try {
                SECONDS.sleep(10);
            } catch (InterruptedException e) {
            }
            logger.info("Asking MidJourney for the status.");
            midResponse = mj.retrieveResponse(getToken(), m.getMessageId());
            logger.info("Progress status: {} ", midResponse.getProgress());
        }

        return midResponse.getResponse();
    }
}