package com.commerz.dvadnyvtahu.ai.service;

import com.commerz.dvadnyvtahu.ai.domain.UserData;
import com.commerz.dvadnyvtahu.ai.repository.UserDataHashMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {

    @Autowired
    private UserDataHashMapRepository repository;
    public UserData getUserDataForId(String id) {
        return repository.getUserDataFromStaticHashMap(id);
    }

}
