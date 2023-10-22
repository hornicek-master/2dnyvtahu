package com.commerz.dvadnyvtahu.ai.controller;

import com.commerz.dvadnyvtahu.ai.client.midjourney.ImageRequest;
import com.commerz.dvadnyvtahu.ai.client.midjourney.Response;
import com.commerz.dvadnyvtahu.ai.service.PromptOrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generate")
public class PromptOrchestrationController {

    @Autowired
    private PromptOrchestrationService promptOrchestrationService;
    @PostMapping
    public void withPrompt(@RequestParam String photoUrl,
                           @RequestParam String gender,
                           @RequestBody ImageRequest ir) {
        promptOrchestrationService.generateImage(photoUrl, gender, "France", ir.getMsg());
    }
}
