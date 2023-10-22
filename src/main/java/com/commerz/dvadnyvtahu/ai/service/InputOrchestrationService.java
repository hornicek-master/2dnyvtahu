package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.domain.UserData;
import io.micrometer.common.util.StringUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InputOrchestrationService {

    private Logger logger = LoggerFactory.getLogger(InputOrchestrationService.class);

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PromptOrchestrationService promptOrchestrationService;

    public void processInput(String photoUrl, String userId) {
        if (StringUtils.isBlank(photoUrl) || StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Mandatory parameters are empty or null");
        }
        UserData userData = fetchUserData(userId);
        String location = fetchLocationPrompt(userData.getActivity());

        String promptToProcess = String.format("%s in %s", userData.getActivity(), location);
        logger.info("Decided user Activity is '{}'.", userData.getActivity());
        logger.info("Determined location is {}.", location);
        promptOrchestrationService.generateImage(photoUrl, userData.getGender(), location, promptToProcess);
    }

    private String fetchLocationPrompt(String activity) {
        return categoryService.fetchLocationPrompt(activity);
    }

    private UserData fetchUserData(String userId) {
        return userDataService.getUserDataForId(userId);
    }
}
