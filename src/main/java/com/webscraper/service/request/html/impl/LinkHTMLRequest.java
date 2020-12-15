package com.webscraper.service.request.html.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.utils.FileIOResourceUtil;
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

    private final String GIVEN_URL;
    private String key;
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    public LinkHTMLRequest(String GIVEN_URL) {
        this(GIVEN_URL, false);
    }

    public LinkHTMLRequest(String GIVEN_URL, boolean toFile) {
        this(GIVEN_URL, toFile, null);
    }

    public LinkHTMLRequest(String GIVEN_URL, boolean toFile, String key) {
        this.GIVEN_URL = GIVEN_URL;
        this.toFile = toFile;
        this.key = key;
    }

    private static List<String> links;

    @Override
    /**
     * Execute LinkRequest
     */
    public void execute(String page) {
        LOG.info("Starting Link Request.");
        if (findLinks(page)) {
            try {
                if (toFile) {
                    runWithFileOutput();
                } else {
                    outputLinks();
                }
            } catch (IOException ex) {
                LOG.error("Failed to write request data to File with error: {}", ex);
                ex.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return GIVEN_URL;
    }

    private boolean findLinks(String page) {
        links = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_HTTP_URL, page, key);
        return !(links.isEmpty());
    }

    private void outputLinks() throws IOException {
        String newLine = System.getProperty("line.separator");
        for (String link : links) {
            if (link.contains("=//")) {
                link = link.replace("=//", "https://");
            }
            if (link.contains("=\"//")) {
                LOG.info("FROM: {}", link);
                link = link.replace("=\"//", "https://");
                LOG.info("TO: {}", link);
            }
            if (toFile) {
                bufferedWriter.append("<a href=\"" + link + "\">" + link + "</a><br>" + newLine);
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
        final String FILE_NAME = "links.html";
        File file = new File(FileIOResourceUtil.FILE_OUT_RESOURCE.get(
                FileIOResourceUtil.HTML_LOCATION) + FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }

}
