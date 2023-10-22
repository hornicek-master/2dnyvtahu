package com.commerz.dvadnyvtahu.ai.controller;

import com.commerz.dvadnyvtahu.ai.client.dto.ChatMessage;
import com.commerz.dvadnyvtahu.ai.service.ImageEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imageEdit")
public class ImageEditorController {

    @Autowired
    private ImageEditService imageEditService;
    @GetMapping
    public ChatMessage edit(@RequestParam String url, @RequestParam String text) {
        imageEditService.addTextToImage(url, text);

        return null;
    }
}
