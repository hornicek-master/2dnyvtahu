package com.commerz.dvadnyvtahu.ai.controller;

import com.commerz.dvadnyvtahu.ai.client.dto.ChatMessage;
import com.commerz.dvadnyvtahu.ai.client.midjourney.ImageRequest;
import com.commerz.dvadnyvtahu.ai.service.ImageService;
import com.commerz.dvadnyvtahu.ai.service.InputOrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/collabothon")
public class MainController {

    @Autowired
    private InputOrchestrationService inputOrchestrationService;

    @Autowired
    private ImageService imageService;

    @PostMapping
    public void ProcessByPhotoUrlAndId(
            @RequestParam String photoUrl,
            @RequestParam String id) {
        inputOrchestrationService.processInput(photoUrl, id);
    }

    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void processByPhotoUploadAndId(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam String id) throws IOException {
        String fileName = "last.jpg";
        String photoUrl = imageService.uploadImage(file, fileName);
        inputOrchestrationService.processInput(photoUrl, id);
    }
}
