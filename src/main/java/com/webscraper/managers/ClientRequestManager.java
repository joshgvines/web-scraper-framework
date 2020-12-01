package com.webscraper.managers;

import com.webscraper.requests.Request;
import com.webscraper.requests.WebScraperRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ClientRequestManager {

    private static HttpClient httpClient;
    private static boolean isHTML;
    private static String pageString;

    public static String attemptClientRequest(String givenURL) {
        httpClient = buildClient();
        try {
            System.out.println(":::Client Attempt::::");
            httpClient.sendAsync(buildRequest(givenURL), HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(ClientRequestManager::parse)
                    .join();
            System.out.println(":::Client Complete::::");
        } catch (Exception e) {
            e.printStackTrace();
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
                .build();
    }

    private static String parse(String html) {
        if (!checkIsHTML(html)) {
            System.out.println("HttpClient Failed To Get Valid Response");
            isHTML = false;
        }
        pageString = html;
        return html;
    }

    private static boolean checkIsHTML(String pageString) {
        return (!pageString.contains("html>")) && (!pageString.contains("HTML>"));
    }

}
