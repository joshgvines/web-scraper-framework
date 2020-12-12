package com.webscraper.demo.cli;

import com.webscraper.services.requests.impl.RequestHandler;

import java.util.Scanner;

public class WebScraperCLI {

    private static Scanner keyboard = new Scanner(System.in);

    public static void runWebScraperCLI() {
        System.out.println("CLI Started");
        System.out.println("Enter /exit to quit...");
        String givenCommand;
        while(true) {
            givenCommand = getUserInput();
            if (givenCommand != null) {
                readCommand(givenCommand);
            }
        }
    }

    /**
     * Each of these processes is a demo for how the scraper can be used.
     * @param givenCommand
     */
    private static void readCommand(final String givenCommand) {
        String givenURI = "example.com";

        if (givenCommand.equals(CLICommand.IMAGE.getCommand())) {
            new RequestHandler().imageRequest(getUserInput());
        } else if (givenCommand.equals(CLICommand.TEXT.getCommand())) {
            new RequestHandler().paragraphRequest(getUserInput());
        } else if (givenCommand.equals(CLICommand.LINK.getCommand())) {
            new RequestHandler().linkRequest(getUserInput());
        } else if (givenCommand.equals(CLICommand.EXIT.getCommand())) {
            System.out.println("exiting");
            System.exit(0);
        } else {
            System.out.println("Not a valid command");
        }
    }
    private static String getUserInput() {
        String userInput = null;
        try {
            userInput = keyboard.nextLine();
        } catch (Exception e) {
            throw new RuntimeException("Failed to run demo command");
        }
        return userInput;
    }
}
