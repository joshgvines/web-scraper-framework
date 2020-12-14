package com.webscraper.service.request.html.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.manager.BufferedRequestManager;
import com.webscraper.manager.ClientRequestManager;
import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.utils.FileIOUtil;
import com.webscraper.service.utils.NetworkUtil;
import com.webscraper.service.utils.RegexPatternUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LinkHTMLRequest implements HTMLRequest {

    private static final Logger LOG = LogManager.getLogger(LinkHTMLRequest.class);

    private String givenUrl;
    private String key;
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    public LinkHTMLRequest(String givenUrl) {
        this(givenUrl, false);
    }

    public LinkHTMLRequest(String givenUrl, boolean toFile) {
        this(givenUrl, toFile, null);
    }

    public LinkHTMLRequest(String givenUrl, boolean toFile, String key) {
        this.givenUrl = givenUrl;
        this.toFile = toFile;
        this.key = key;
    }

    private static List<String> links;

    @Override
    /**
     * Execute LinkRequest
     */
    public void execute() {
        LOG.info("Starting Link Request.");
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = null;
            try {
                page = ClientRequestManager.attemptClientRequest(givenUrl);
            } catch (Exception ex) {
                LOG.error("Web location was valid, but failed to open client connection.");
                try {
                    page = BufferedRequestManager.attemptBufferedRequest(givenUrl);
                } catch (Exception badex) {
                    badex.printStackTrace();
                }
            }
            if (findLinks(page)) {
                try {
                    if (toFile) {
                        runWithFileOutput();
                    } else {
                        outputLinks();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private boolean findLinks(String page) {
        links = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_ANY_HTTPS_URL, page, key);
        return !(links.isEmpty());
    }

    private void outputLinks() throws IOException {
        String newLine = System.getProperty("line.separator");
        for (String link : links) {
            if (link.contains("=//")) {
                link = link.replace("=//", "https://");
            }
            if (toFile) {
                bufferedWriter.append("<a href=\""+link+"\">"+ link +"</a><br>" + newLine);
//                bufferedWriter.append(link + newLine);
            }
            System.out.println(link);
        }
    }

    private void runWithFileOutput() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(buildLinkFile())) {
            this.bufferedWriter = bufferedWriter;
            outputLinks();
        }
    }

    private FileWriter buildLinkFile() throws IOException {
        File file = new File(FileIOUtil.HTML_LOC + "link.html");
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }

}
