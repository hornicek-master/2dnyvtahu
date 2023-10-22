package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.client.dto.ChatMessage;
import com.commerz.dvadnyvtahu.ai.repository.CategoryRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChatService chatService;

    public String fetchLocationPrompt(String activity) {
        String instruction = categoryRepository.getLocationPrompt(activity);

        ChatMessage chatGptResponse = chatService.prompt(instruction);
        String content = chatGptResponse.getContent();

        if (StringUtils.isNotBlank(content)) {
            CSVReader reader = new CSVReader(new StringReader(content));
            try {
                String[] locations = reader.readNext();
                String[] locationParts = locations[0].split(",");
                return locationParts[0];
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
//            int randomNum = ThreadLocalRandom.current().nextInt(1, 6);
//            return parts[randomNum];
        }

        return null;
    }
}
