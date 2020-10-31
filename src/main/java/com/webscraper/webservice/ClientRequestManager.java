package com.webscraper.webservice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ClientRequestManager {

    private HttpClient httpClient;
    private static boolean isHTML;

    public HttpClient buildClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public boolean attemptHttpClientRequest(String givenURL) {
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
        return isHTML;
    }

    private static boolean checkIsHTML(String pageString) {
        return (!pageString.contains("html>")) && (!pageString.contains("HTML>"));
    }

    private static String parse(String pageString) {
        if (!checkIsHTML(pageString)) {
            System.out.println("HttpClient Failed To Get Valid Response");
            isHTML = false;
        }
        ResponseManager.processWebPageData(pageString);
        isHTML = true;
        return pageString;
    }

    private HttpRequest buildRequest(String givenURL) {
        return HttpRequest.newBuilder()
                .uri(URI.create(givenURL))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }

}
