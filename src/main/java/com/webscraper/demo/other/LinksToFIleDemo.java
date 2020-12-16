package com.webscraper.demo.other;

import com.webscraper.WebScraper;
import com.webscraper.filters.HtmlFilter;

public class LinksToFIleDemo {

    public static void main(String[] args) {

        WebScraper.runHTMLRequest(true, HtmlFilter.FIND_PARAGRAPH,
                "http://example.com/", null);

    }
}
