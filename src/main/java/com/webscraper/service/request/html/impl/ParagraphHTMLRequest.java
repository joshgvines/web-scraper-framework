package com.webscraper.service.request.html.impl;

import com.webscraper.filters.HtmlFilter;
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

public class ParagraphHTMLRequest implements HTMLRequest {

    private static final Logger LOG = LogManager.getLogger(ParagraphHTMLRequest.class);

    private final String GIVEN_URL;
    private String key;
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    public ParagraphHTMLRequest(String GIVEN_URL) {
        this(GIVEN_URL, false);
    }

    public ParagraphHTMLRequest(String GIVEN_URL, boolean toFile) {
        this(GIVEN_URL, toFile, null);
    }

    public ParagraphHTMLRequest(String GIVEN_URL, boolean toFile, String key) {
        this.GIVEN_URL = GIVEN_URL;
        this.toFile = toFile;
        this.key = key;
    }

    private static List<String> paragraphs;

    @Override
    /**
     * Execute ParagraphRequest
     */
    public void execute(String page) {
        LOG.info("Starting Paragraph Request.");
        if (findParagraphs(page)) {
            try {
                if (toFile) {
                    runWithFileOutput();
                } else {
                    outputParagraphs();
                }
            } catch (IOException ex) {
                LOG.error("Failed to write data to File from: {} with error: {}", GIVEN_URL, ex);
                ex.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return GIVEN_URL;
    }

    private boolean findParagraphs(String page) {
        paragraphs = RegexPatternUtil.lookForHTMLMatches(HtmlFilter.FIND_PARAGRAPH, page, key);
        return !(paragraphs.isEmpty());
    }

    private void outputParagraphs() throws IOException {
        String newLine = System.getProperty("line.separator");
        for (String paragraph : paragraphs) {
            if (toFile) {
                bufferedWriter.append(paragraph + newLine);
            }
            System.out.println(paragraph);
        }
    }

    private void runWithFileOutput() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(buildLinkFile())) {
            this.bufferedWriter = bufferedWriter;
            outputParagraphs();
        }
    }

    private FileWriter buildLinkFile() throws IOException {
        File file = new File(FileIOUtil.HTML_LOC + "paragraphs.html");
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }

}
