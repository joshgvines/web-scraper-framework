package com.webscraper.enums;

public enum CLICommand {

    SCRAPE_FOR_IMAGE("/image"),
    SCRAPE_FOR_TEXT("/text"),

    EXIT("/exit");

    private String command;

    CLICommand(String givenCommand) {
        this.command = givenCommand;
    }

    public String getCommand() {
        return command;
    }

}
