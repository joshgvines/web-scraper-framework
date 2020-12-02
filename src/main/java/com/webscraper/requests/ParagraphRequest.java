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
        String pageString = ClientRequestManager.attemptClientRequest(givenUrl);
        String[] lines = pageString.split(System.getProperty("line.separator"));
        if (findParagraphs(lines)) {
            outputParagraphs();
        }
    }

    public boolean isValid(String key) {
        return true;
    }

    public static void processWebPageData(String pageString) {
        if (pageString.length() >= 2 && (pageString.contains("/html>") || pageString.contains("/HTML>"))) {

        }
    }

    private static boolean findParagraphs(String[] lines) {
        //StringBuilder pageContent = new StringBuilder();
        paragraphs = new ArrayList();

        boolean inside = false;
        String aLine = "";
        for (String line : lines) {
            aLine = line;
            try {
                Thread.sleep(1000000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            while (line.contains("<script")) {
//                System.out.println(line);
//                String toRemove = line.substring(line.indexOf("<script"), line.indexOf("</script>"));
//                System.out.println(toRemove);
//                line.replace(toRemove, "");
//            }

            if (line.contains(HtmlFilter.FIND_PARAGRAPH.getFilter()) && line.contains("</p>")) {
                paragraphs.add(line);
                inside = false;
            } else if (line.contains("</p>")) {
                paragraphs.add(line);
                inside = false;
            } else if (line.trim().contains(HtmlFilter.FIND_PARAGRAPH.getFilter())) {
                paragraphs.add(line);
                inside = true;
            } else if (inside) {
                paragraphs.add(line);
            }
            //System.out.println(line);
            //pageContent.append(line);
        }
        System.out.println(aLine);
        return !( paragraphs.isEmpty() );
    }

    private static void outputParagraphs() {
        for (String link : paragraphs) {
            System.out.println(link);
        }
    }

}
