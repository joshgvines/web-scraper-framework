package com.webscraper.enums;

public enum Filter {

    FIND_PARAGRAPH("<p>"),
    END_PARAGRAPH("</p>"),

    FIND_IMAGE("<img>"),
    END_IMAGE("</img>"),

    FIND_HTTPS_URL("http:"),
    FIND_HTTP_URL("https:");

    private String filter;

    Filter(String givenFilter) {
        this.filter = givenFilter;
    }

    public String getFilter() {
        return filter;
    }

}
