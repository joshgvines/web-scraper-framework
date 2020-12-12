package com.webscraper.services.requests.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;
import com.webscraper.services.requests.Request;
import com.webscraper.services.utils.NetworkUtil;
import com.webscraper.services.utils.RegexPatternUtil;

import java.util.List;

public class LinkRequest implements Request {

    protected LinkRequest() {
    }

    private static List<String> links;

    @Override
    /**
     *
     */
    public void execute(String givenUrl) {
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findLinks(page)) {
                outputLinks();
            }
        }
    }

    private static boolean findLinks(String page) {
        links = RegexPatternUtil.lookForMatches(HtmlFilter.FIND_ANY_HTTPS_URL, page);
        return !(links.isEmpty());
    }

    private static void outputLinks() {
        for (String link : links) {
            System.out.println(link);
        }
    }

}
