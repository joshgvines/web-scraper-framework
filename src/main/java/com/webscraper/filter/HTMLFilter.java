package com.webscraper.filter;

import java.util.ArrayList;
import java.util.List;

public class HTMLFilter {

    private List<String> imageURIList = new ArrayList<>();
    private List<String> paragraphList = new ArrayList<>();

    public void getImages() {
        System.out.println("images");
    }

    public void getParagraphs() {
        System.out.println("paragraphs");
    }

}
