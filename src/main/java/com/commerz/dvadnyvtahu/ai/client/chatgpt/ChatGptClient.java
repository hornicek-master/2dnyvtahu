package com.commerz.dvadnyvtahu.ai.client.chatgpt;

import com.commerz.dvadnyvtahu.ai.client.dto.ChatRequestDto;
import com.commerz.dvadnyvtahu.ai.client.dto.ChatResponseDto;
import com.commerz.dvadnyvtahu.ai.configuration.FeignConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ChatGptClient", url = "https://api.openai.com/v1/chat", configuration = FeignConfig.class)
@Headers("Authorization: {token}")
public interface ChatGptClient {

    @GetMapping("/completions")
    ChatResponseDto executePrompt(@RequestHeader("Authorization") String token, ChatRequestDto request);

}
