package com.webscraper.requests;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.managers.ClientRequestManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphRequest implements Request {

    private static ArrayList<String> paragraphs;

    @Override
    /**
     *
     * @param pageString
     */
    public void execute(String givenUrl) {
        System.out.println("Paragraph Request");
        String page = ClientRequestManager.attemptClientRequest(givenUrl);
        if (findParagraphs(page)) {
            outputParagraphs();
        }
    }

    public boolean isValid() {
        return true;
    }

    public static void processWebPageData(String pageString) {
        if (pageString.length() >= 2 && (pageString.contains("/html>") || pageString.contains("/HTML>"))) {

        }
    }

    private static boolean findParagraphs(String page) {
        paragraphs = new ArrayList();
        Pattern p = Pattern.compile(HtmlFilter.FIND_PARAGRAPH.getFilter());
        Matcher m = p.matcher(page);
        while (m.find()) {
            String link = m.group();
            if (!paragraphs.contains(link)) {
                paragraphs.add(m.group());
            }
        }
        return !( paragraphs.isEmpty() );
    }

    private static void outputParagraphs() {
        for (String para : paragraphs) {
            System.out.println(para);
        }
    }

}
