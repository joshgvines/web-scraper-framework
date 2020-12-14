package com.webscraper.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Util class for validating network connections.
 */
public final class NetworkUtil {

    private static final Logger LOG = LogManager.getLogger(NetworkUtil.class);

    public static boolean checkConnectionIsValid(String givenURL) {
        try {
            URL urlToCheck = new URL(givenURL);
            HttpURLConnection connection = (HttpURLConnection) urlToCheck.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();
            return (responseCode == 200);

        } catch (Exception ex) {
            LOG.error("Failed to create a valid URL connection with: {} with error: {}", givenURL, ex);
            ex.printStackTrace();
            return false;
        }
    }
}
