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
import java.net.URL;
import java.util.List;

/**
 * ImageHTMLRequest
 */
public class ImageHTMLRequest implements HTMLRequest {

    private static final Logger LOG = LogManager.getLogger(ImageHTMLRequest.class);

    private String givenUrl;
    private String key;
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    /**
     * All default request configuration constructor.
     *
     * @param givenUrl
     */
    public ImageHTMLRequest(String givenUrl) {
        this(givenUrl, false);
    }

    public ImageHTMLRequest(String givenUrl, boolean toFile) {
        this(givenUrl, toFile, null);
    }

    public ImageHTMLRequest(String givenUrl, boolean toFile, String key) {
        this.givenUrl = givenUrl;
        this.toFile = toFile;
        this.key = key;
    }

    private List<String> images;

    @Override
    /**
     * Execute ImageRequest
     */
    public void execute() {
        LOG.info("Starting Image Request.");
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);;
//            try {
//                page = ClientRequestManager.attemptClientRequest(givenUrl);
//            } catch (Exception ex) {
//                LOG.error("Web location was valid, but failed to open client connection.");
//                try {
//                    page = BufferedRequestManager.attemptBufferedRequest(givenUrl);
//                } catch (Exception badex) {
//                    badex.printStackTrace();
//                }
//            }

            if (findImages(page)) {
                try {
                    if (toFile) {
                        runWithFileOutput();
                    } else {
                        outputImages();
                    }
                } catch (IOException ex) {
                    LOG.error("Failed to write data to File from: {} with error: {}", givenUrl, ex);
                    ex.printStackTrace();
                }
            }
        }
    }

    private boolean findImages(String page) {
        images = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_IMAGE, page, key);
        return !(images.isEmpty());
    }

    private void outputImages() throws IOException {
        String newLine = System.getProperty("line.separator");
        for (String link : images) {
            if (link.contains("=//")) {
                link = link.replace("//", "https://");
            }
            // Force all urls to be individual image tags.
            if (link.contains("data-src=")) {
                List<String> urls = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_ANY_HTTPS_URL, link, null);
                link = "";
                for (String url : urls) {
                    link = link + "<img src=\"" + url + "\" alt=blah height=100 width=100 >" + newLine;
                }
            }
            if (toFile) {
                bufferedWriter.append(link + newLine);
            }
            System.out.println(link);
        }
    }

    private void runWithFileOutput() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(buildImageFile())) {
            this.bufferedWriter = bufferedWriter;
            outputImages();
        }
    }

    private FileWriter buildImageFile() throws IOException {
        File file = new File(FileIOUtil.HTML_LOC + "images.html");
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }
}
