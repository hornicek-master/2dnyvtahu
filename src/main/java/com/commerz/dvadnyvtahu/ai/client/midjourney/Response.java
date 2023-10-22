package com.commerz.dvadnyvtahu.ai.client.midjourney;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    String imageUrl;
    List<String> imageUrls;
}
