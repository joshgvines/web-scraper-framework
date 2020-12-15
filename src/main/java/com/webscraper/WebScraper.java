package com.webscraper;

import com.webscraper.controller.impl.TagHTMLRequestController;
import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.utils.FileIOUtil;
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
     * @param toFile
     * @param requestType
     * @param givenUrl
     */
    public static void runHTMLRequest(boolean toFile, HtmlFilter requestType, String givenUrl)  {
        if (toFile) {
            buildEnvironment();
        }
        TagHTMLRequestController controller = TagHTMLRequestController.getHandler();
        switch (requestType) {
            case FIND_IMAGE: controller.imageRequest(givenUrl, toFile);
                break;
            case FIND_PARAGRAPH: controller.paragraphRequest(givenUrl, toFile);
                break;
            case FIND_ANY_HTTPS_URL: controller.linkRequest(givenUrl, toFile);
                break;
            default: System.out.println("RequestType Not Supported...");
        }
    }

    private static boolean buildEnvironment() {
        Properties properties = FileIOUtil.readProperties();
        if (properties != null) {
            properties.getProperty("location.root");
            properties.getProperty("location.js");
            properties.getProperty("location.html");
            properties.getProperty("location.css");
        } else {
            if (!FileIOUtil.buildDefaultEnvironment()) {
                LOG.error("Failed to build output environment.");
                throw new IllegalStateException("Failed to build output environment.");
            }
        }
    }


}
