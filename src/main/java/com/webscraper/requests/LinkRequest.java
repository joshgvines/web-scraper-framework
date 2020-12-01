package com.webscraper.requests;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkRequest implements WebScraperRequest {

    private static ArrayList<String> links;

    public void execute(String pageString) {
        System.out.println("Link Request");
        String[] lines = pageString.split(System.getProperty("line.separator"));
        if (readLinesForLinks(lines)) {
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

    private static boolean readLinesForLinks(String[] lines) {
        StringBuilder pageContent = new StringBuilder();
        links = new ArrayList();

        for (String line : lines) {
            String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
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
