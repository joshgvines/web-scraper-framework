package com.webscraper.webservice;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseManager {

    public static void processWebPageData(String daFaq) {
        String[] lines = daFaq.split(System.getProperty("line.separator"));
        boolean hasLines = false;

        ArrayList<String> links = new ArrayList();
        StringBuilder pageContent = new StringBuilder();
        for (String line : lines) {
            if (line.length() >= 3 && line.contains("<a>")) {
                hasLines = true;
            }
            String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(line);

            while (m.find()) {
                String link = m.group();
                if (!links.contains(link)) {
                    links.add(m.group());
                }
            }
            if (line.contains("/html>") || line.contains("/HTML>")) {
                System.out.println(line);
                break;
            }
            System.out.println(line);
            pageContent.append(line);
        }
        if ((lines.length < 1) && (!hasLines)) {
            System.out.println("Filter did not catch anything");
        }
        System.out.println("\n::LINKS:::::");
        for (String link : links) {
            System.out.println(link);
        }

    }

}
