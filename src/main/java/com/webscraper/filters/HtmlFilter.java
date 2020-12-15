package com.webscraper.filters;

/**
 * enum class which maps Regex and string filters to request types and functions.
 */
public enum HtmlFilter {

    FIND_PARAGRAPH("<p(.*?)p>"),

    FIND_IMAGE("<img(.*?)>"),

    FIND_ANY_HTTP_URL("\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]"),
    FIND_HTTP_URL("\\(?\\b(https://|http://|www[.]|=//|=\"//)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]");

    private String filter;

    HtmlFilter(String givenFilter) {
        this.filter = givenFilter;
    }

    public String getFilter() {
        return filter;
    }

}
