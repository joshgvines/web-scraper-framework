package com.webscraper.services.requests.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.services.requests.Request;
import com.webscraper.services.requests.WebScraperRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controller for basic Request types.
 * Each request type is split into individual methods for ease of use from the API perspective.
 */
public class RequestHandler implements WebScraperRequest {

    private ExecutorService execService = Executors.newCachedThreadPool();

    @Override
    public void imageRequest(String givenUrl) {
        manageSingleRequestExecution(new ImageRequest(givenUrl));
    }

    @Override
    public void linkRequest(String givenUrl) {
        manageSingleRequestExecution(new LinkRequest(givenUrl));
    }

    @Override
    public void paragraphRequest(String givenUrl) {
        manageSingleRequestExecution(new ParagraphRequest(givenUrl));
    }

    @Override
    public void multiRequest(List<Request> requestList) throws InterruptedException {
        for (Request req : requestList) {
            processMultiRequestExecution(req);
        }
        execService.shutdown();
        while (!execService.awaitTermination(2, TimeUnit.SECONDS)) ;
    }

    private void manageSingleRequestExecution(Request request) {
        request.execute();
    }

    /**
     * @param request
     */
    private void processMultiRequestExecution(Request request) {
        if (request != null) {
            try {
                execService.execute(new ExecuteRequestRunnable(request));
            } catch (Exception ex) {
                execService.shutdown();
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    /**
     * Nested class for executing Runnable requests as asynchronous threads.
     */
    private static class ExecuteRequestRunnable implements Runnable {

        private Request request;

        private ExecuteRequestRunnable(Request request) {
            this.request = request;
        }

        @Override
        /**
         * Execute request in runnable.
         */
        public void run() {
            request.execute();
        }
    }

}