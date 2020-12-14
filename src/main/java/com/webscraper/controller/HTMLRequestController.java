package com.webscraper.controller;

import com.webscraper.service.request.html.HTMLRequest;

import java.util.List;

/**
 * Defines primary request types.
 */
public interface HTMLRequestController {

    /**
     * Create initial request object to gather all images from given URL for processing.
     *
     * @param givenUrl
     */
    void imageRequest(String givenUrl, boolean toFile);

    /**
     * Gather all Links from given URL
     *
     * @param givenUrl
     */
    void linkRequest(String givenUrl, boolean toFile);

    /**
     * Gather all Paragraphs from given URL
     *
     * @param givenUrl
     */
    void paragraphRequest(String givenUrl, boolean toFile);

    /**
     * Add requests to collection to be processed asynchronously.
     *
     * TODO: this should be more of a queue or dequeue...
     *
     * @param HTMLRequestList
     * @throws InterruptedException
     */
    void multiRequest(List<HTMLRequest> HTMLRequestList) throws InterruptedException;

}
