package com.webscraper.requests;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;
import com.webscraper.requests.utils.NetworkUtil;
import com.webscraper.requests.utils.RegexPatternUtil;
;
import java.util.List;

public class ImageRequest implements Request {

    private static List<String> images;

    @Override
    public void execute(String givenUrl) {
        if (isValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findImages(page)) {
                outputImages();
            }
        }
    }

    public boolean isValid(String givenUrl) {
        return NetworkUtil.checkConnectionIsValid(givenUrl);
    }

    private static boolean findImages(String page) {
        images = RegexPatternUtil.lookForMatches(HtmlFilter.FIND_IMAGE, page);
        return !( images.isEmpty() );
    }

    private static void outputImages() {
        for (String link : images) {
            System.out.println(link);
        }
    }
}
