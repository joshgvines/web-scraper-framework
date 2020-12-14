package com.webscraper.controller;

import com.webscraper.service.request.html.HTMLRequest;

import java.util.List;

/**
 * Defines primary request types.
 */
public interface ScriptRequestController {

    /**
     * Gather all JavaScript found from given URL.
     *
     * @param givenUrl
     */
    void allScripts(String givenUrl);

    /**
     * Gather all JavaScript functions found from given URL.
     *
     * @param givenUrl
     */
    void allFunctions(String givenUrl);

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
