package com.webscraper.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Handles File IO and output directory processes.
 */
public final class FileIOResourceUtil {

    private static final Logger LOG = LogManager.getLogger(FileIOResourceUtil.class);

    // Default Environment Variables
    // TODO: Make this a configuration file and resource loader.
    public static Map<String, String> FILE_OUT_RESOURCE = new HashMap<>();
    private static final String fs = File.separator;

    private static final String PROPERTIES_FILE = "webscraper.properties";
    private static String root = "public" + fs;
    private static String jsLocation = root + "generated" + fs + "web" + fs + "js" + fs;
    private static String htmlLocation = root + "generated" + fs + "web" + fs + "html" + fs;
    private static String cssLocation = root + "generated" + fs + "web" + fs + "css" + fs;
    private static String configLocation = root + "config" + fs;

    public static final String JS_LOCATION_KEY = "jsLocation";
    public static final String HTML_LOCATION_KEY = "htmlLocation";
    public static final String CSS_LOCATION_KEY = "cssLocation";
    public static final String CONFIG_LOCATION_KEY = "configLocation";

    public static boolean buildEnvironment() {
        try {
            Properties properties = FileIOResourceUtil.readPropertiesFile();
            if (!addResources(properties)) {
                throw new IllegalStateException("Failed to build output environment.");
            }
        } catch (Exception ex) {
            LOG.error("Failed to build output environment with error: {}", ex);
            System.exit(0);
        }
        return true;
    }

    /**
     * Create environment for file output.
     * TODO: this process should be refactored and should only run if the environment does not exist.
     * TODO: the user should also be able to override where they want output to go.
     *
     * @return boolean isCreated
     */
    private static boolean addResources(Properties properties) throws IOException {
        if (properties != null) {
            // TODO: add config here if not null
        }
        FILE_OUT_RESOURCE.put(JS_LOCATION_KEY, jsLocation + fs);
        FILE_OUT_RESOURCE.put(HTML_LOCATION_KEY, htmlLocation + fs);
        FILE_OUT_RESOURCE.put(CSS_LOCATION_KEY, cssLocation + fs);
        FILE_OUT_RESOURCE.put(CONFIG_LOCATION_KEY, configLocation + fs);
        buildDirs();
        return !(FILE_OUT_RESOURCE.isEmpty());
    }

    public static String getResource(String key) {
        return FILE_OUT_RESOURCE.get(key);
    }

    /**
     * Create file output environment locations.
     */
    private static void buildDirs() throws IOException {
        for (Map.Entry<String, String> location : FILE_OUT_RESOURCE.entrySet()) {
            if (location.getValue() != null) {
                File file = new File(location.getValue());
                if (!file.exists()) {
                    if (location.getValue().contains(configLocation)) {
                        file.mkdirs();
                        file = new File(configLocation + PROPERTIES_FILE);
                        file.createNewFile();
                    } else {
                        file.mkdirs();
                    }
                }
            }
        }
    }

    public static Properties readPropertiesFile() {
        try {
            File file = new File(configLocation + PROPERTIES_FILE);
            if (file.exists()) {
                Properties properties = new Properties();
                properties.load(new FileInputStream(file));
                return properties;
            }
        } catch (Exception ex) {
            LOG.error("Failed to load properties file: {}", ex);
        }
        LOG.info("No Properties File Found, using default.");
        return null;
    }
}
