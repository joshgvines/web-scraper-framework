package com.webscraper.services.requests.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;
import com.webscraper.services.requests.Request;
import com.webscraper.services.utils.NetworkUtil;
import com.webscraper.services.utils.RegexPatternUtil;

import java.util.List;

public class ImageRequest implements Request {

    protected ImageRequest() {
    }

    private static List<String> images;

    @Override
    /**
     *
     */
    public void execute(String givenUrl) {
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
