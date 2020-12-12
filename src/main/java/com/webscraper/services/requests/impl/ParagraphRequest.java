package com.webscraper.services.requests.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;
import com.webscraper.services.requests.Request;
import com.webscraper.services.utils.NetworkUtil;
import com.webscraper.services.utils.RegexPatternUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphRequest implements Request {

    public ParagraphRequest() {
    }

    private static List<String> paragraphs;

    @Override
    /**
     *
     * @param pageString
     */
    public void execute(String givenUrl) {
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findParagraphs(page)) {
                outputParagraphs();
            }
        }
    }

    private static boolean findParagraphs(String page) {
        paragraphs = RegexPatternUtil.lookForMatches(HtmlFilter.FIND_PARAGRAPH, page);
        return !(paragraphs.isEmpty());
    }

    private static void outputParagraphs() {
        for (String para : paragraphs) {
            System.out.println(para);
        }
    }

}
