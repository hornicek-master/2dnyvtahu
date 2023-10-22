package com.commerz.dvadnyvtahu.ai.controller;

import com.commerz.dvadnyvtahu.ai.client.midjourney.ImageRequest;
import com.commerz.dvadnyvtahu.ai.client.midjourney.Response;
import com.commerz.dvadnyvtahu.ai.service.MidJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mj")
public class MjController {

    @Autowired
    private MidJourneyService midJourneyService;

    @GetMapping("/imagine/{what}")
    public Response initImagine(@PathVariable String what) {
        return midJourneyService.testMj(what);
    }

    @PostMapping("/imagine")
    public Response withPrompt(@RequestBody ImageRequest ir) {
        return midJourneyService.callMidJourney(ir);
    }
}