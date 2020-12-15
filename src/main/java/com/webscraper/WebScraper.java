package com.webscraper;

import com.webscraper.controller.impl.TagHTMLTagRequestController;
import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.utils.FileIOResourceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

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

    public static void runHTMLRequest(boolean toFile, HtmlFilter requestType, String givenUrl, String key) {
        if (toFile) {
            buildEnvironment();
        }
        TagHTMLTagRequestController controller = TagHTMLTagRequestController.getInstance();
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

    private static boolean buildEnvironment() {
        Properties properties = FileIOResourceUtil.readPropertiesFile();
        if (properties != null) {
            if (!FileIOResourceUtil.buildOverrideEnvironment(properties)) {
                LOG.error("Failed to build output environment.");
                throw new IllegalStateException("Failed to build output environment.");
            }
        } else {
            if (!FileIOResourceUtil.buildDefaultEnvironment()) {
                LOG.error("Failed to build output environment.");
                throw new IllegalStateException("Failed to build output environment.");
            }
        }
        return true;
    }

}
