package com.webscraper.services.requests.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;
import com.webscraper.services.requests.Request;
import com.webscraper.services.utils.NetworkUtil;
import com.webscraper.services.utils.RegexPatternUtil;

import java.util.List;

public class ImageRequest implements Request {

    private String givenUrl = "";

    public ImageRequest(String givenUrl) {
        this.givenUrl = givenUrl;
    }

    private static List<String> images;

    @Override
    /**
     * Execute ImageRequest
     */
    public void execute() {
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findImages(page)) {
                outputImages();
            }
        }
    }

    private static boolean findImages(String page) {
        images = RegexPatternUtil.lookForMatches(HtmlFilter.FIND_IMAGE, page);
        return !(images.isEmpty());
    }

    private static void outputImages() {
        for (String link : images) {
            System.out.println(link);
        }
    }
}
