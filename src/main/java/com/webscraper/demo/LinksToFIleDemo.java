package com.webscraper.demo;

import com.webscraper.WebScraper;
import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.request.html.impl.ImageHTMLRequest;
import com.webscraper.service.request.html.impl.LinkHTMLRequest;

public class LinksToFIleDemo {

    public static void main(String[] args) {

//         new ImageHTMLRequest(
//                 "https://www.edureka.co/blog/dynamic-web-pages-in-java/",
//                 true, ".svg"
//         ).execute();

        new ImageHTMLRequest(
                "https://github.com/joshgvines/mockomatik/tree/feature_e/create_complex_test_method_templates/src/main/resources",
                true, null
        ).execute();

        new LinkHTMLRequest(
                "https://skillcrush.com/break-into-tech-blueprint",
                true, null
        ).execute();

//        WebScraper.runHTMLRequest(
//                true, HtmlFilter.FIND_IMAGE,
//                "https://www.edureka.co/blog/dynamic-web-pages-in-java/");
//
//        WebScraper.runHTMLRequest(
//                true, HtmlFilter.FIND_ANY_HTTPS_URL,
//                "https://www.edureka.co/blog/dynamic-web-pages-in-java/");
//
//        WebScraper.runHTMLRequest(
//                true, HtmlFilter.FIND_PARAGRAPH,
//                "https://www.edureka.co/blog/dynamic-web-pages-in-java/");
    }
}
