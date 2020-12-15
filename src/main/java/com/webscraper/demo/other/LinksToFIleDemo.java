package com.webscraper.demo.other;

import com.webscraper.WebScraper;
import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.request.html.impl.ImageHTMLRequest;
import com.webscraper.service.request.html.impl.LinkHTMLRequest;

public class LinksToFIleDemo {

    public static void main(String[] args) {

        WebScraper.runHTMLRequest(true,HtmlFilter.FIND_IMAGE,
                "https://github.com/joshgvines/web-scraper-framework", null);

    }
}
