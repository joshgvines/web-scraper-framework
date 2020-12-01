package com.webscraper.requests;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkRequest implements Request {

    private static ArrayList<String> links;

    @Override
    /**
     *
     * @param pageString
     */
    public void execute(String givenUrl) {
        System.out.println("Link Request");
        String pageString = ClientRequestManager.attemptClientRequest(givenUrl);
        String[] lines = pageString.split(System.getProperty("line.separator"));
        if (findLinks(lines)) {
            outputLinks();
        }
    }

    public boolean isValid(String key) {
        return true;
    }

    public static void processWebPageData(String pageString) {
        if (pageString.length() >= 2 && (pageString.contains("/html>") || pageString.contains("/HTML>"))) {

        }
    }

    private static boolean findLinks(String[] lines) {
        StringBuilder pageContent = new StringBuilder();
        links = new ArrayList();

        for (String line : lines) {
            Pattern p = Pattern.compile(HtmlFilter.FIND_ANY_URL.getFilter());
            Matcher m = p.matcher(line);
            while (m.find()) {
                String link = m.group();
                if (!links.contains(link)) {
                    links.add(m.group());
                }
            }
            System.out.println(line);
            pageContent.append(line);
        }
        return !( links.isEmpty() );
    }

    private static void outputLinks() {
        for (String link : links) {
            System.out.println(link);
        }
    }

}
