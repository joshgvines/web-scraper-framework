package com.webscraper.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Handles File IO and output directory processes.
 */
public final class FileIOResourceUtil {

    private static final Logger LOG = LogManager.getLogger(FileIOResourceUtil.class);

    // Default Environment Variables
    // TODO: Make this a configuration file and resource loader.
    public static Map<String, String> FILE_OUT_RESOURCE = new HashMap<>();
    private static final String BREAK = File.separator;

    private static String jsDefaultLocation = "." + BREAK + "output" + BREAK + "js" + BREAK;
    private static String htmlDefaultLocation = "." + BREAK + "output" + BREAK + "html" + BREAK;
    private static String cssDefaultLocation = "." + BREAK + "output" + BREAK + "css" + BREAK;

    public static final String JS_LOCATION = "jsLocation";
    public static final String HTML_LOCATION = "htmlLocation";
    public static final String CSS_LOCATION = "cssLocation";

    /**
     * Create environment for file output.
     * TODO: this process should be refactored and should only run if the environment does not exist.
     * TODO: the user should also be able to override where they want output to go.
     *
     * @return boolean isCreated
     */
    public static boolean buildDefaultEnvironment() {
        FILE_OUT_RESOURCE.put(JS_LOCATION, jsDefaultLocation);
        FILE_OUT_RESOURCE.put(HTML_LOCATION, htmlDefaultLocation);
        FILE_OUT_RESOURCE.put(CSS_LOCATION, cssDefaultLocation);
        buildDirs();
        return !(FILE_OUT_RESOURCE.isEmpty());
    }

    public static boolean buildOverrideEnvironment(Properties properties) {
        FILE_OUT_RESOURCE.put(JS_LOCATION, properties.getProperty(JS_LOCATION) + BREAK);
        FILE_OUT_RESOURCE.put(HTML_LOCATION, properties.getProperty(HTML_LOCATION) + BREAK);
        FILE_OUT_RESOURCE.put(CSS_LOCATION, properties.getProperty(CSS_LOCATION) + BREAK);
        buildDirs();
        return !(FILE_OUT_RESOURCE.isEmpty());
    }

    /**
     * Create file output environment locations.
     */
    private static void buildDirs() {
        for (Map.Entry<String, String> location : FILE_OUT_RESOURCE.entrySet()) {
            File file = new File(location.getValue());
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public static Properties readPropertiesFile() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL fileLoc = classloader.getResource("webscraper.properties");
        if (fileLoc != null) {
            try {
                File file = new File(fileLoc.getPath());
                if (file.exists()) {
                    Properties properties = new Properties();
                    properties.load(new FileInputStream(file));
                    return properties;
                }
            } catch (Exception ex) {
                LOG.error("Failed to load properties file: {}", ex);
            }
        }
        LOG.info("No Properties File Found, using default.");
        return null;
    }
}
