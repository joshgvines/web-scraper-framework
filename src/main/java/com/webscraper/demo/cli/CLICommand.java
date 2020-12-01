package com.webscraper.demo.cli;

public enum CLICommand {

    IMAGE("/image"),
    TEXT("/text"),
    LINK("/link"),
    HELP("/help"),

    EXIT("/exit");

    private String command;

    CLICommand(String givenCommand) {
        this.command = givenCommand;
    }

    public String getCommand() {
        return command;
    }

}
