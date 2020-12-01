package com.webscraper.filters;

public enum HtmlFilter {

    FIND_PARAGRAPH("<p>"),
    END_PARAGRAPH("</p>"),

    FIND_IMAGE("<img>"),
    END_IMAGE("</img>"),

    FIND_HTTPS_URL("http:"),
    FIND_HTTP_URL("https:"),
    FIND_ANY_URL("\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]");

    private String filter;

    HtmlFilter(String givenFilter) {
        this.filter = givenFilter;
    }

    public String getFilter() {
        return filter;
    }

}
