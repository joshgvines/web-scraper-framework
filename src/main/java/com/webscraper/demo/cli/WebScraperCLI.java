package com.webscraper.demo.cli;

import com.webscraper.controller.impl.HTMLRequestController;

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
        HTMLRequestController requestHandler = HTMLRequestController.getInstance();
        if (givenCommand.equals(CLICommand.IMAGE.getCommand())) {
            requestHandler.imageRequest(getUserInput(), false, null);
        } else if (givenCommand.equals(CLICommand.TEXT.getCommand())) {
            requestHandler.paragraphRequest(getUserInput(), false, null);
        } else if (givenCommand.equals(CLICommand.LINK.getCommand())) {
            requestHandler.linkRequest(getUserInput(), false, null);
        } else if (givenCommand.equals(CLICommand.EXIT.getCommand())) {
            System.out.println("exiting");
            System.exit(0);
        } else {
            System.out.println("Not a valid command");
        }
    }
    private static String getUserInput() {
        String userInput;
        try {
            userInput = keyboard.nextLine();
        } catch (Exception e) {
            throw new RuntimeException("Failed to run demo command");
        }
        return userInput;
    }
}
