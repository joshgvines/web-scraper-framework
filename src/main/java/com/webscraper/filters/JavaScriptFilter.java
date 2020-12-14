package com.webscraper.filters;

/**
 * Select script type and corresponding filter type to extract desired data.
 */
public enum JavaScriptFilter {

    ALL_SCRIPT_TAGS("<script(.*?)script>");

    private String filter;

    JavaScriptFilter(String givenFilter) {
        this.filter = givenFilter;
    }

    public String getFilter() {
        return filter;
    }

}
