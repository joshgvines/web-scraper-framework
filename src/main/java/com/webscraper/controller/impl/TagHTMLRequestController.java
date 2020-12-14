package com.webscraper.controller.impl;

import com.webscraper.controller.HTMLRequestController;
import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.request.html.impl.ImageHTMLRequest;
import com.webscraper.service.request.html.impl.LinkHTMLRequest;
import com.webscraper.service.request.html.impl.ParagraphHTMLRequest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controller for basic Request types.
 * Each request type is split into individual methods for ease of use from the API perspective.
 */
public final class TagHTMLRequestController implements HTMLRequestController {

    private static ExecutorService execService = null;
    private static TagHTMLRequestController requestHandler = null;

    /**
     * Lazy singleton initialization with Double check locking
     *
     * @return RequestHandler this
     */
    public static TagHTMLRequestController getHandler() {
        //Synchronized block to remove overhead.
        synchronized (TagHTMLRequestController.class) {
            if (requestHandler == null) {
                requestHandler = new TagHTMLRequestController();
            }
        }
        return requestHandler;
    }
    private TagHTMLRequestController() { }

    @Override
    public void imageRequest(String givenUrl, boolean toFile) {
        manageSingleRequestExecution(new ImageHTMLRequest(givenUrl, toFile));
    }

    @Override
    public void linkRequest(String givenUrl, boolean toFile) {
        manageSingleRequestExecution(new LinkHTMLRequest(givenUrl, toFile));
    }

    @Override
    public void paragraphRequest(String givenUrl, boolean toFile) {
        manageSingleRequestExecution(new ParagraphHTMLRequest(givenUrl, toFile));
    }

    @Override
    public void multiRequest(List<HTMLRequest> HTMLRequestList) throws InterruptedException {
        execService = Executors.newCachedThreadPool();
        // Init requests in thread Executor service.
        for (HTMLRequest HTMLRequest : HTMLRequestList) {
            processMultiRequestExecution(HTMLRequest);
        }
        execService.shutdown();
        while (!execService.awaitTermination(2, TimeUnit.SECONDS)) ;
    }

    /**
     * Process a single request.
     *
     * @param HTMLRequest
     */
    private void manageSingleRequestExecution(HTMLRequest HTMLRequest) {
        HTMLRequest.execute();
    }

    /**
     * Process multiple requests.
     *
     * @param HTMLRequest
     */
    private void processMultiRequestExecution(HTMLRequest HTMLRequest) {
        if (HTMLRequest != null) {
            try {
                execService.execute(new ExecuteRequestRunnable(HTMLRequest));
            } catch (Exception ex) {
                execService.shutdownNow();
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    /**
     * Nested class for executing Runnable requests as asynchronous threads.
     */
    private static class ExecuteRequestRunnable implements Runnable {

        private HTMLRequest HTMLRequest;

        private ExecuteRequestRunnable(HTMLRequest HTMLRequest) {
            this.HTMLRequest = HTMLRequest;
        }

        @Override
        /**
         * Execute request in runnable.
         */
        public void run() {
            HTMLRequest.execute();
        }
    }

}