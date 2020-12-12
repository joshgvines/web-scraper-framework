package com.webscraper.services.utils;

import com.webscraper.filters.HtmlFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for looping through strings to find regex expression matching substrings.
 */
public class RegexPatternUtil {

    /**
     * Find all matching substrings via regex compiled pattern.
     * @param filter
     *      Enum that should contain regex expression.
     * @param page
     *      A string containing the entire page content.
     * @return List<String> pageContentList
     *      All found matches.
     */
    public static List<String> lookForMatches(HtmlFilter filter, String page) {
        List<String> pageContentList = new ArrayList<>();
        Pattern p = Pattern.compile(filter.getFilter());
        Matcher m = p.matcher(page);
        while (m.find()) {
            String link = m.group();
            if (!pageContentList.contains(link)) {
                pageContentList.add(m.group());
            }
        }
        return pageContentList;
    }

}
