package com.webscraper.filters;

public enum HtmlFilter {

    FIND_PARAGRAPH("<p(.*?)p>"),
    END_PARAGRAPH("\\b</p>|/p>"),

    FIND_IMAGE("\\b<img|<img>"),
    END_IMAGE("\\b</img>|/>"),

    FIND_ANY_HTTP_URL("\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]"),
    FIND_ANY_HTTPS_URL("\\(?\\b(https://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]");

    private String filter;

    HtmlFilter(String givenFilter) {
        this.filter = givenFilter;
    }

    public String getFilter() {
        return filter;
    }

}
