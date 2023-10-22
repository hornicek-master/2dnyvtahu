package com.commerz.dvadnyvtahu.ai.client.dto;

// add Lombok annotations
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ChatRequestDto {

    private String model;
    private List<ChatMessage> messages;
    private int n = 1;
    private double temperature = 0.9;

    public ChatRequestDto(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new ChatMessage("user", prompt));
    }

}
