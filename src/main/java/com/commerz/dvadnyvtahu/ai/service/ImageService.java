package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.client.gcs.GoogleCloudServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ImageService {
    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private GoogleCloudServicesClient googleCloudServicesClient;

    @Value("${google.cloud.storage.token}")
    private String token;

    private String getToken() {
        return "Bearer " + token;
    }

    public String uploadImage(MultipartFile file, String fileName) throws IOException {

        logger.info("Uploading image {} to the google cloud.",file.getName());
        googleCloudServicesClient.uploadImage(getToken(), fileName, file.getBytes());
        logger.info("Uploading image completed. The link is {}" + "https://storage.googleapis.com/collabothon-dvadnyvtahu/" + fileName);

        return "https://storage.googleapis.com/collabothon-dvadnyvtahu/" + fileName;
    }
}
