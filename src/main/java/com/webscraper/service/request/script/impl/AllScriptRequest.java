package com.webscraper.service.request.script.impl;

import com.webscraper.filters.JavaScriptFilter;
import com.webscraper.manager.ClientRequestManager;
import com.webscraper.service.request.script.ScriptRequest;
import com.webscraper.service.utils.NetworkUtil;
import com.webscraper.service.utils.RegexPatternUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Find all scripts on given page and process data to output.
 */
public class AllScriptRequest implements ScriptRequest {

    private String givenUrl = "";
    private boolean toFile;
    private BufferedWriter bufferedWriter;

    public AllScriptRequest(String givenUrl) {
        this(givenUrl, false);
    }

    public AllScriptRequest(String givenUrl, boolean toFile) {
        this.givenUrl = givenUrl;
        this.toFile = toFile;
    }

    private List<String> scripts;

    @Override
    /**
     * Execute ImageRequest
     */
    public void execute() {
        if (NetworkUtil.checkConnectionIsValid(givenUrl)) {
            String page = ClientRequestManager.attemptClientRequest(givenUrl);
            if (findAllScripts(page)) {
                try {
                    if (toFile) {
                        runWithFileOutput();
                    } else {
                        outputImages();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    /**
     * Attempt to run function in dynamic environment.
     */
    public boolean runScript() {
        return false;
    }

    private boolean findAllScripts(String page) {
        scripts = RegexPatternUtil.lookForScriptMatches(JavaScriptFilter.ALL_SCRIPT_TAGS, page);
        return !(scripts.isEmpty());
    }

    private void outputImages() throws IOException {
        String newLine = System.getProperty("line.separator");
        for (String link : scripts) {
            if (toFile) {
                bufferedWriter.append(link + newLine);
            }
            System.out.println(link);
        }
    }

    private void runWithFileOutput() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(buildScriptFile())) {
            this.bufferedWriter = bufferedWriter;
            outputImages();
        }
    }

    /**
     * Create location to ouput found script to .html file for processing.
     * @return
     * @throws IOException
     */
    private FileWriter buildScriptFile() throws IOException {
        String outputLocation = "." + File.separator+"output"
                + File.separator + "js" + File.separator;

        File file = new File( outputLocation + "JavaScripts.html");
        if (!file.exists()) {
            File outputDir = new File(outputLocation);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            file.createNewFile();
        }
        return new FileWriter(file);
    }
}