package com.webscraper.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Handles File IO and output directory processes.
 */
public final class FileIOUtil {

    // Default Environment Variables
    // TODO: Make this a configuration file and resource loader.
    public static final String JS_LOC = "." + File.separator + "output" + File.separator + "js" + File.separator;
    public static final String HTML_LOC = "." + File.separator + "output" + File.separator + "html" + File.separator;

    /**
     * Create environment for file output.
     * TODO: this process should be refactored and should only run if the environment does not exist.
     * TODO: the user should also be able to override where they want output to go.
     *
     * @return boolean isCreated
     */
    public static boolean buildDefaultEnvironment() {
        List<File> locations = new ArrayList<>();
        locations.add( new File(JS_LOC)   );
        locations.add( new File(HTML_LOC) );

        for (File location : locations) {
            // Create Environment locations.
            if (!location.exists()) {
                location.mkdirs();
            }
        }
        for (File location : locations) {
            // Validate Environment location.
            if (!location.exists()) {
                return false;
            }
        }
        return true;
    }

    public static Properties readProperties() {
        FileInputStream fileInputStream = null;
        Properties properties = null;
        try {
            File file = new File("webscraper.properties");
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                properties = new Properties();
                properties.load(fileInputStream);
                return properties;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
