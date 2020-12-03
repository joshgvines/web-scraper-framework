package com.webscraper.requests;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;
import com.webscraper.requests.utils.NetworkUtil;
import com.webscraper.requests.utils.RegexPatternUtil;

import java.util.List;

public class LinkRequest implements Request {

    private static List<String> links;

    @Override
    public void execute(String givenUrl) {
        if (isValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findLinks(page)) {
                outputLinks();
            }
        }
    }

    public boolean isValid(String givenUrl) {
        return NetworkUtil.checkConnectionIsValid(givenUrl);
    }

    private static boolean findLinks(String page) {
        links = RegexPatternUtil.lookForMatches(HtmlFilter.FIND_ANY_HTTPS_URL, page);
        return !( links.isEmpty() );
    }

    private static void outputLinks() {
        for (String link : links) {
            System.out.println(link);
        }
    }

}
