package com.webscraper.service.utils;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.filters.JavaScriptFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for looping through strings to find regex expression matching substrings.
 */
public final class RegexPatternUtil {

    /**
     * Find all matching substrings via regex compiled pattern.
     * @param filter
     *      Enum that should contain regex expression.
     * @param page
     *      A string containing the entire page content.
     * @param key
     *      Used to only add found matches that contain specific desired content.
     * @return List<String> pageContentList
     *      All found matches.
     */
    public static List<String> lookForHTMLMatches(HtmlFilter filter, String page, String key) {
        List<String> pageContentList = new ArrayList<>();
        Pattern pattern = Pattern.compile(filter.getFilter());
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
            String foundMatch = matcher.group();
            // Make sure the found content is not a duplicate.
            if (!pageContentList.contains(foundMatch)) {
                // If a key was given, only add matches that contain desired data.
                if (key != null) {
                    if (foundMatch.contains(key)) {
                        pageContentList.add(foundMatch);
                    }
                } else {
                    pageContentList.add(foundMatch);
                }
            }
        }
        return pageContentList;
    }

    /**
     * TODO: this will change via needs for script regex filter needs, or will be deleted.
     * @param filter
     * @param page
     * @return
     */
    public static List<String> lookForScriptMatches(JavaScriptFilter filter, String page) {
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
