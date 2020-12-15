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

        WebScraper.runHTMLRequest(true,HtmlFilter.FIND_IMAGE,
                "https://skillcrush.com/break-into-tech-blueprint", null);


//        new LinkHTMLRequest(
//                "https://skillcrush.com/break-into-tech-blueprint",
//                true, null
//        );
    }
}
