package com.webscraper.requests;

import com.webscraper.managers.ClientRequestManager;

/**
 * Defines primary request types.
 */
public interface WebScraperRequest {

    /**
     * Gather all images from given URL
     * @param givenUrl
     */
    void imageRequest(String givenUrl);

    /**
     * Gather all Links from given URL
     * @param givenUrl
     */
    void linkRequest(String givenUrl);

    /**
     * Gather all Paragraphs from given URL
     * @param givenUrl
     */
    void paragraphRequest(String givenUrl);

}
