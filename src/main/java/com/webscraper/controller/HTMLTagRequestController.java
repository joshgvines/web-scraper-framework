package com.webscraper.controller;

import com.webscraper.service.request.html.HTMLRequest;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Defines primary request types.
 */
public interface HTMLTagRequestController {

    /**
     * Create initial request object to gather all images from given URL for processing.
     *
     * @param givenUrl
     */
    void imageRequest(String givenUrl, boolean toFile, String key);

    /**
     * Gather all Links from given URL
     *
     * @param givenUrl
     */
    void linkRequest(String givenUrl, boolean toFile, String key);

    /**
     * Gather all Paragraphs from given URL
     *
     * @param givenUrl
     */
    void paragraphRequest(String givenUrl, boolean toFile, String key);

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
