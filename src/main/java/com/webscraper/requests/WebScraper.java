package com.webscraper.requests;

import com.webscraper.managers.ClientRequestManager;

/**
 * Controller for Request types.
 */
public class WebScraper implements WebScraperRequest {

    private Request request;

    public void imageRequest(String givenUrl) {
        request = new ImageRequest();
        request.execute(givenUrl);
        manageResponseData();
    }

    public void linkRequest(String givenUrl) {
        request = new LinkRequest();
        request.execute(givenUrl);
        manageResponseData();
    }

    public void paragraphRequest(String givenUrl) {
        request = new ParagraphRequest();
        request.execute(givenUrl);
        manageResponseData();
    }

    private void manageResponseData() {
        System.out.println("manage response data");
    }

}
