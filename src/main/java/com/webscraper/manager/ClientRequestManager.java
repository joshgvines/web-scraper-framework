package com.webscraper.manager;

import com.webscraper.WebScraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ClientRequestManager {

    private static final Logger LOG = LogManager.getLogger(WebScraper.class);

    private static HttpClient httpClient;
    private static String pageString;

    public static String attemptClientRequest(String givenURL) {
        httpClient = buildClient();
        try {
            String preDomain = givenURL.substring(0, givenURL.indexOf("."));
            LOG.info("Attempting Client Request: {}", preDomain);
            httpClient.sendAsync(buildRequest(givenURL), HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(ClientRequestManager::parse)
                    .join();
            LOG.info("Closing Client Request With: {}", preDomain);
        } catch (Exception e) {
            e.printStackTrace();
            pageString = "BAD_CONN_ERROR";
        }
        return pageString;
    }

    public static HttpClient buildClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    private static HttpRequest buildRequest(String givenURL) {
        return HttpRequest.newBuilder()
                .uri(URI.create(givenURL))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
//                .header("accept", "application/json")
                .build();
    }

    /**
     * Set local variable to page source.
     * @param html
     * @return String
     */
    private static String parse(String html) {
        if (!checkIsHTML(html)) {
            LOG.warn("HttpClient failed to find valid HTML");
        } else {
            LOG.info("HttpClient found valid HTML.");
        }
        pageString = html;
        return html;
    }

    private static boolean checkIsHTML(String pageString) {
        return (pageString.contains("html>")) || (pageString.contains("HTML>"));
    }

}
