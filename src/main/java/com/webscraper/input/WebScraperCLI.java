package com.webscraper.input;

import com.webscraper.enums.CLICommand;
import com.webscraper.webservice.ImageRequest;
import com.webscraper.webservice.ParagraphRequest;

import java.util.Scanner;

public class WebScraperCLI {

    private Scanner keyboard = new Scanner(System.in);

    public void runWebScraperCLI() {
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
    private void readCommand(final String givenCommand) {
        String givenURI = "example.com";
        if (givenCommand.equals(CLICommand.SCRAPE_FOR_IMAGE.getCommand().toLowerCase())) {
            System.out.println("image command");
            ImageRequest imageRequest = new ImageRequest(getUserInput());
        } else if (givenCommand.equals(CLICommand.SCRAPE_FOR_TEXT.getCommand().toLowerCase())) {
            System.out.println("text command");
            ParagraphRequest paragraphRequest = new ParagraphRequest(getUserInput());
        } else if (givenCommand.equals(CLICommand.EXIT.getCommand().toLowerCase())) {
            System.out.println("exiting");
            System.exit(0);
        } else {
            System.out.println("Not a valid command");
        }
    }
    private String getUserInput() {
        String userInput = null;
        try {
            userInput = keyboard.nextLine();
        } catch (Exception e) {
            // TODO:
        }
        return userInput;
    }
}
