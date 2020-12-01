package com.webscraper.requests;

public class ImageRequest implements WebScraperRequest {

    @Override
    public void execute(String pageString) {

    }

    public boolean isValid(String key) {
        return true;
    }
}
