package com.webscraper.service.request.html.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.utils.FileIOUtil;
import com.webscraper.service.utils.RegexPatternUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * ImageHTMLRequest
 */
public class ImageHTMLRequest implements HTMLRequest {

    private static final Logger LOG = LogManager.getLogger(ImageHTMLRequest.class);

    private final String GIVEN_URL;
    private String key;
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    /**
     * All default request configuration constructor.
     *
     * @param GIVEN_URL
     */
    public ImageHTMLRequest(String GIVEN_URL) {
        this(GIVEN_URL, false);
    }

    public ImageHTMLRequest(String GIVEN_URL, boolean toFile) {
        this(GIVEN_URL, toFile, null);
    }

    public ImageHTMLRequest(String GIVEN_URL, boolean toFile, String key) {
        this.GIVEN_URL = GIVEN_URL;
        this.toFile = toFile;
        this.key = key;
    }

    private List<String> images;

    @Override
    /**
     * Execute ImageRequest
     */
    public void execute(String page) {
        LOG.info("Starting Image Request.");
        if (findImages(page)) {
            try {
                if (toFile) {
                    runWithFileOutput();
                } else {
                    outputImages();
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

    private boolean findImages(String page) {
        images = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_IMAGE, page, key);
        return !(images.isEmpty());
    }

    private void outputImages() throws IOException {
        String newLine = System.getProperty("line.separator");
        for (String image : images) {
            if (image.contains("=//")) {
                image = image.replace("//", "https://");
            }
            // Force all urls to be individual image tags.
            if (image.contains("data-src=")) {
                List<String> urls = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_HTTP_URL, image, null);
                image = "";
                for (String url : urls) {
                    image = image + "<img src=\"" + url + "\" alt=blah height=100 width=100 >" + newLine;
                }
            }
            if (toFile) {
                bufferedWriter.append(image + newLine);
            }
            System.out.println(image);
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
