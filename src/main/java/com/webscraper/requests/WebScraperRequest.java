package com.webscraper.requests;

public interface WebScraperRequest {

    void execute(String pageString);

    boolean isValid(String key);

}
