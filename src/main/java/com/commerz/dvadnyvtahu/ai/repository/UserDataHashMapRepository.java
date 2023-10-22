package com.commerz.dvadnyvtahu.ai.repository;

import com.commerz.dvadnyvtahu.ai.domain.UserData;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class UserDataHashMapRepository {

    private static final Map<String, UserData> USER_DATA_MAP = Map.ofEntries(
        new AbstractMap.SimpleEntry<>("jean1234", new UserData("male",
            List.of("wine", "Bordeau", "basketball"))),
            new AbstractMap.SimpleEntry<>("scarlet666", new UserData("female",
            List.of("relaxing on a beach"))),
            new AbstractMap.SimpleEntry<>("michal", new UserData("male",
                    List.of("relaxing on a beach")))
    );

    public UserData getUserDataFromStaticHashMap(String id) {
        if (Strings.isBlank(id)) {
            return null;
        }
        return USER_DATA_MAP.get(id);
    }

}
