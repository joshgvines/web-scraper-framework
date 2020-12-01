package com.webscraper.requests;

import com.webscraper.managers.ClientRequestManager;

public class RequestController {

    public static void imageRequest(String givenUrl) {
        ClientRequestManager.attemptHttpClientRequest(givenUrl, new ImageRequest());
    }

    public static void linkRequest(String givenUrl) {
        ClientRequestManager.attemptHttpClientRequest(givenUrl, new LinkRequest());
    }

    public static void paragraphRequest(String givenUrl) {
        ClientRequestManager.attemptHttpClientRequest(givenUrl, new ParagraphRequest());
    }

}
