package com.webscraper.requests;

import com.webscraper.managers.ClientRequestManager;

import java.util.ArrayList;
import java.util.List;

public class ParagraphRequest implements WebScraperRequest {

    private List<String> imageUrls = new ArrayList<>();

    public void execute(String pageString) {
        System.out.println("Paragraph Request");
    }

    public boolean isValid(String key) {
        return true;
    }

}
