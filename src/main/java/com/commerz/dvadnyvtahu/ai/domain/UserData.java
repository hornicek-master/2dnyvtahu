package com.commerz.dvadnyvtahu.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserData {

    private String gender;
    private List<String> favourites;

    public String getActivity() {
        return favourites.get(0);
    }

}
