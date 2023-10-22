package com.commerz.dvadnyvtahu.ai.repository;

import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;

@Component
public class CategoryRepository {

    private static final Map<String, String> CATEGORY_PROMOPT_MAP = Map.ofEntries(
        new AbstractMap.SimpleEntry<>("vacation",
            "give me 5 the most favourite vacation destinations where i can %s CSV format without numbers")
    );

    public String getLocationPrompt(String activity) {
        return String.format(CATEGORY_PROMOPT_MAP.get("vacation"), activity);
    }

}
