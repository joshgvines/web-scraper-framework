package com.webscraper.services.requests;

import java.util.List;
import java.util.Map;

/**
 * Defines primary request types.
 */
public interface WebScraperRequest {

    /**
     * Create initial request object to gather all images from given URL for processing.
     *
     * @param givenUrl
     */
    void imageRequest(String givenUrl);

    /**
     * Gather all Links from given URL
     *
     * @param givenUrl
     */
    void linkRequest(String givenUrl);

    /**
     * Gather all Paragraphs from given URL
     *
     * @param givenUrl
     */
    void paragraphRequest(String givenUrl);

    /**
     * Add requests to collection to be processed asynchronously.
     *
     * TODO: this should be more of a queue or dequeue...
     *
     * @param requestList
     * @throws InterruptedException
     */
    void multiRequest(List<Request> requestList) throws InterruptedException;

}
