package com.webscraper.controller.impl;

import com.webscraper.controller.HTMLTagRequestController;
import com.webscraper.manager.ClientRequestManager;
import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.request.html.impl.ImageHTMLRequest;
import com.webscraper.service.request.html.impl.LinkHTMLRequest;
import com.webscraper.service.request.html.impl.ParagraphHTMLRequest;
import com.webscraper.service.utils.NetworkUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controller for basic Request types.
 * Each request type is split into individual methods for ease of use from the API perspective.
 */
public final class TagHTMLTagRequestController implements HTMLTagRequestController {

    private static final Logger LOG = LogManager.getLogger(TagHTMLTagRequestController.class);

    private static ExecutorService execService = null;
    private static TagHTMLTagRequestController requestHandler = null;

    /**
     * Lazy singleton initialization with Double check locking
     *
     * @return RequestHandler this
     */
    public static TagHTMLTagRequestController getInstance() {
        //Synchronized block to remove overhead.
        synchronized (TagHTMLTagRequestController.class) {
            if (requestHandler == null) {
                requestHandler = new TagHTMLTagRequestController();
            }
        }
        return requestHandler;
    }

    private TagHTMLTagRequestController() {
    }

    @Override
    public void imageRequest(String givenUrl, boolean toFile, String key) {
        manageSingleRequestExecution(new ImageHTMLRequest(givenUrl, toFile, key));
    }

    @Override
    public void linkRequest(String givenUrl, boolean toFile, String key) {
        manageSingleRequestExecution(new LinkHTMLRequest(givenUrl, toFile, key));
    }

    @Override
    public void paragraphRequest(String givenUrl, boolean toFile, String key) {
        manageSingleRequestExecution(new ParagraphHTMLRequest(givenUrl, toFile, key));
    }

    @Override
    public void multiRequest(List<HTMLRequest> hTMLRequestList) throws InterruptedException {
        execService = Executors.newCachedThreadPool();
        // Init requests in thread Executor service.
        for (HTMLRequest hTMLRequest : hTMLRequestList) {
            processMultiRequestExecution(hTMLRequest);
        }
        execService.shutdown();
        while (!execService.awaitTermination(2, TimeUnit.SECONDS)) ;
    }

    /**
     * Process a single request.
     *
     * @param hTMLRequest
     */
    private void manageSingleRequestExecution(HTMLRequest hTMLRequest) {
        final String GIVEN_URL = hTMLRequest.getUrl();
        if (NetworkUtil.checkConnectionIsValid(GIVEN_URL)) {
            LOG.info("Test connection to {} was successful",
                    GIVEN_URL.substring(0, GIVEN_URL.lastIndexOf(".")));
            String page = ClientRequestManager.attemptClientRequest(GIVEN_URL);
            if (page != null && (page.contains("html") || page.contains("HTML"))) {
                System.out.println(page);
                hTMLRequest.execute(page);
            }

        }

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
                LOG.error("Failed to execute mutli request with error: {}", ex);
                execService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Nested class for executing Runnable requests as asynchronous threads.
     * TODO: ExecutionService has better ways to abstract this logic.
     */
    private static class ExecuteRequestRunnable implements Runnable {

        private static final Logger LOG = LogManager.getLogger(ExecuteRequestRunnable.class);

        private final HTMLRequest HTML_REQUEST;
        private final String GIVEN_URL;

        private ExecuteRequestRunnable(HTMLRequest hTMLRequest) {
            this.HTML_REQUEST = hTMLRequest;
            this.GIVEN_URL = hTMLRequest.getUrl();
        }

        @Override
        /**
         * Execute request in runnable.
         */
        public void run() {
            if (NetworkUtil.checkConnectionIsValid(GIVEN_URL)) {
                LOG.info("Test connection to {} was successful",
                        GIVEN_URL.substring(0, GIVEN_URL.lastIndexOf(".")));
                String page = ClientRequestManager.attemptClientRequest(GIVEN_URL);
                HTML_REQUEST.execute(page);
            }
            LOG.info("Request Thread End");
        }
    }

}