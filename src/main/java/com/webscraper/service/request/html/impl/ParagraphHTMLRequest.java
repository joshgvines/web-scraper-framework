package com.webscraper.service.request.html.impl;

import com.webscraper.filters.HtmlFilter;
import com.webscraper.manager.ClientRequestManager;
import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.utils.FileIOUtil;
import com.webscraper.service.utils.NetworkUtil;
import com.webscraper.service.utils.RegexPatternUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class ParagraphHTMLRequest implements HTMLRequest {

    private String givenUrl;
    private String key;
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    public ParagraphHTMLRequest(String givenUrl) {
        this(givenUrl, false);
    }

    public ParagraphHTMLRequest(String givenUrl, boolean toFile) {
        this.givenUrl = givenUrl;
        this.toFile = toFile;
    }

    private static List<String> paragraphs;

    @Override
    /**
     * Execute ParagraphRequest
     */
    public void execute() {
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findParagraphs(page)) {
                try {
                    if (toFile) {
                        runWithFileOutput();
                    } else {
                        outputParagraphs();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
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
