package com.webscraper.requests;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageRequest implements Request {

    private static ArrayList<String> images;

    @Override
    /**
     *
     */
    public void execute(String givenUrl) {
        System.out.println("Image Request");
        String page = ClientRequestManager.attemptClientRequest(givenUrl);
        //String[] lines = pageString.split(System.getProperty("line.separator"));
        if (findImages(page)) {
            outputLinks();
        }
    }

    public boolean isValid() {
        return true;
    }

    private static boolean findImages(String page) {
        images = new ArrayList();
        Pattern p = Pattern.compile(HtmlFilter.FIND_IMAGE.getFilter());
        Matcher m = p.matcher(page);
        while (m.find()) {
            String link = m.group();
            if (!images.contains(link)) {
                images.add(m.group());
            }
        }
        return !( images.isEmpty() );
    }

    private static void outputLinks() {
        for (String link : images) {
            System.out.println(link);
        }
    }
}
