package com.webscraper.webservice;

import java.util.ArrayList;
import java.util.List;

public class ParagraphRequest {

    private List<String> imageUrls = new ArrayList<>();

    public ParagraphRequest(final String givenURI) {

        ClientRequestManager clientRequestManager = new ClientRequestManager(givenURI);

        clientRequestManager.attemptHttpClientRequest();

    }

//    private List<String> scanFor() {
//
//        String imageUrl;
//        for ()
//
//        imageUrls.add(imageUrl);
//    }

}
