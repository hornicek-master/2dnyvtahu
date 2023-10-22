package com.commerz.dvadnyvtahu.ai.client.dto;


import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ChatResponseDto {

    private List<Choice> choices;

    @Getter
    public static class Choice {
        private int index;
        private ChatMessage message;
    }
}

