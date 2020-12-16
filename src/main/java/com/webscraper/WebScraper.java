package com.webscraper;

import com.webscraper.controller.impl.HTMLRequestController;
import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.utils.FileIOResourceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Highest level of singular request abstraction.
 */
public class WebScraper {

    private static final Logger LOG = LogManager.getLogger(WebScraper.class);

    /**
     * Highest level of singular request abstraction.
     *
     * @param toFile
     * @param requestType
     * @param givenUrl
     */
    public static void runHTMLRequest(boolean toFile, HtmlFilter requestType, String givenUrl) {
        runHTMLRequest(toFile, requestType, givenUrl, null);
    }

    /**
     *
     * @param toFile
     * @param requestType
     * @param givenUrl
     * @param key
     */
    public static void runHTMLRequest(boolean toFile, HtmlFilter requestType, String givenUrl, String key) {
        if (toFile) {
            FileIOResourceUtil.buildEnvironment();
        }
        HTMLRequestController controller = HTMLRequestController.getInstance();
        switch (requestType) {
            case FIND_IMAGE: controller.imageRequest(givenUrl, toFile, key);
                break;
            case FIND_PARAGRAPH: controller.paragraphRequest(givenUrl, toFile, key);
                break;
            case FIND_HTTP_URL: controller.linkRequest(givenUrl, toFile, key);
                break;
            default: System.out.println("RequestType Not Supported...");
        }
    }

}
