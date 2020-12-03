package com.webscraper.requests.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Util class for validating network connections.
 */
public class NetworkUtil {

    public static boolean checkConnectionIsValid(String givenURL) {
        try {
            URL urlToCheck = new URL(givenURL);
            HttpURLConnection connection = (HttpURLConnection) urlToCheck.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();
            return (responseCode == 200);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
