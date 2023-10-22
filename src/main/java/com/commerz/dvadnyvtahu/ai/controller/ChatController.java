package com.commerz.dvadnyvtahu.ai.controller;

import com.commerz.dvadnyvtahu.ai.client.dto.ChatMessage;
import com.commerz.dvadnyvtahu.ai.client.dto.ChatRequestDto;
import com.commerz.dvadnyvtahu.ai.client.dto.ChatResponseDto;
import com.commerz.dvadnyvtahu.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ChatMessage chat(@RequestParam String prompt) {
        return chatService.prompt(prompt);
    }
}
