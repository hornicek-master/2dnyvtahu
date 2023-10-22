package com.commerz.dvadnyvtahu.ai.client.midjourney;

import com.commerz.dvadnyvtahu.ai.configuration.FeignConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "MidJourneyClient", url = "https://api.thenextleg.io/v2", configuration = FeignConfig.class)
@Headers("Authorization: {token}")
public interface MidJourneyClient {
    @PostMapping("/imagine")
    Message executePrompt(@RequestHeader("Authorization") String token, ImageRequest request);

    @GetMapping("/message/{handle}")
    MidResponse retrieveResponse(@RequestHeader("Authorization") String token, @PathVariable String handle);
}
