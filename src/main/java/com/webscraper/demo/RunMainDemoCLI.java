package com.webscraper.demo;

import com.webscraper.demo.cli.WebScraperCLI;

public class RunMainDemoCLI {

    public static void main(String[] args) {

        try {
            // Basic command line interface for demo
            WebScraperCLI.runWebScraperCLI();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
