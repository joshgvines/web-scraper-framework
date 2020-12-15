package com.webscraper.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.InetSocketAddress;

/**
 * Util class for validating network connections.
 */
public final class NetworkUtil {

    private static final Logger LOG = LogManager.getLogger(NetworkUtil.class);

    public static boolean checkConnectionIsValid(String givenURL) {
        try {

            return (new InetSocketAddress(givenURL, 80).isUnresolved());

        } catch (Exception ex) {
            LOG.error("Failed to create a valid Socket connection with: {} with error: {}", givenURL, ex);
            return false;
        }
    }
}
