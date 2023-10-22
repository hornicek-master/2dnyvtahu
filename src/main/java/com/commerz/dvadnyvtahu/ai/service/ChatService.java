package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.client.chatgpt.ChatGptClient;
import com.commerz.dvadnyvtahu.ai.client.dto.ChatMessage;
import com.commerz.dvadnyvtahu.ai.client.dto.ChatRequestDto;
import com.commerz.dvadnyvtahu.ai.client.dto.ChatResponseDto;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatGptClient chatGpt;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.key}")
    private String token;

    @Value("${prompt.chatgtp.template}")
    private String promptTemplate;

    private String getToken() {
        return "Bearer " + token;
    }

    public ChatMessage prompt(String prompt) {

        ChatRequestDto request = new ChatRequestDto(model, String.format(promptTemplate, prompt));

        logger.info("ChatGPT executing prompt: {} ", WordUtils.wrap(request.getMessages().get(0).getContent(), 150));
        ChatResponseDto response = chatGpt.executePrompt(getToken(), request);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }
        logger.info("ChatGPT response: {} ", WordUtils.wrap(response.getChoices().get(0).getMessage().getContent(), 150));

        // return the first response
        return response.getChoices().get(0).getMessage();
    }


    //Does not work
//    public ImageUpload uploadFile() {
//        Resource resource = resourceLoader.getResource("classpath:images/drug.png");
//        try {
//            StringBuilder binary = new StringBuilder();
//            DataInputStream input = new DataInputStream(resource.getInputStream());
//            try {
//                while (true) {
//                    binary.append(Integer.toBinaryString(input.readByte()));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            byte[] fileContent = resource.getContentAsByteArray();
//            String encodedString = Base64.getEncoder().encodeToString(fileContent);
//
//            ImageUpload iu = new ImageUpload();
//            iu.setFile(encodedString);
//            iu.setFile(binary.toString());
//            ImageUploadResponse iur = fi.uploadImage(iu);
//
//            return mj.getAddressByEmployeeId("Bearer " + token, ir);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}