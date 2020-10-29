package com.webscraper.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BufferedRequestManager {

    public boolean attemptBufferedRequest(URL url) throws IOException {
        URLConnection urlConn = url.openConnection();
        urlConn.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, " +
                "like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        return getBufferedPageSource(urlConn.getInputStream());
    }

    private boolean checkIsHTML(String daFaq) {
        return (!daFaq.contains("html>")) && (!daFaq.contains("HTML>"));
    }

    private boolean getBufferedPageSource(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try ( BufferedReader br = new BufferedReader( new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()).trim() != "") {
                sb.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String daFaq = sb.toString();
        if (!checkIsHTML(daFaq)) {
            System.out.println("Buffered request failed to get a valid response");
            return false;
        }
        ResponseManager.processWebPageData(daFaq);
        return true;
    }

}
