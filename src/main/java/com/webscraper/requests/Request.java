package com.webscraper.requests;

public interface Request {

    void execute(String givenUrl);

    boolean isValid(String dataToValidate);

}
