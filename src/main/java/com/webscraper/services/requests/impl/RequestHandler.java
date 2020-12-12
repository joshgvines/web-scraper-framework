package com.webscraper.services.requests.impl;

import com.webscraper.services.requests.Request;
import com.webscraper.services.requests.WebScraperRequest;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controller for basic Request types.
 */
public class RequestHandler implements WebScraperRequest {

    private ExecutorService execService = Executors.newCachedThreadPool();

    @Override
    public void imageRequest(String givenUrl) {
        manageSingleRequestExecution(new ImageRequest(), givenUrl);
    }

    @Override
    public void linkRequest(String givenUrl) {
        manageSingleRequestExecution(new LinkRequest(), givenUrl);
    }

    @Override
    public void paragraphRequest(String givenUrl) {
        manageSingleRequestExecution(new ParagraphRequest(), givenUrl);
    }

    @Override
    public void multiRequest(Map<String, Request> requestMap) throws InterruptedException {
        for (Map.Entry<String, Request> request : requestMap.entrySet()) {
            processMultiRequestExecution(request.getValue(), request.getKey());
        }
        execService.shutdown();
        while (!execService.awaitTermination(2, TimeUnit.SECONDS)) ;
    }

    private void manageSingleRequestExecution(Request request, String givenUrl) {
        request.execute(givenUrl);
    }

    /**
     * @param request
     * @param givenUrl
     */
    private void processMultiRequestExecution(Request request, String givenUrl) {
        if (request != null) {
            try {
                execService.execute(new ExecuteRequestRunnable(request, givenUrl));
            } catch (Exception ex) {
                execService.shutdown();
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    /**
     * Nested class for executing requests
     */
    private static class ExecuteRequestRunnable implements Runnable {

        private Request request;
        private String givenUrl;

        private ExecuteRequestRunnable(Request request, String givenUrl) {
            this.request = request;
            this.givenUrl = givenUrl;
        }

        @Override
        /**
         * Execute request in runnable.
         */
        public void run() {
            request.execute(givenUrl);
        }
    }

}