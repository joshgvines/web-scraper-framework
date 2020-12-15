package com.webscraper.demo;

import com.webscraper.service.request.html.HTMLRequest;
import com.webscraper.service.request.html.impl.ParagraphHTMLRequest;
import com.webscraper.controller.impl.TagHTMLRequestController;

import java.util.ArrayList;
import java.util.List;

public class MultiRequestDemo {

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.nanoTime();

        System.out.println("Running Multi Req" + System.lineSeparator());

        List<HTMLRequest> HTMLRequestMap = new ArrayList<>();
        HTMLRequestMap.add(new ParagraphHTMLRequest("https://stackoverflow.com/questions/2275443/how-to-timeout-a-thread"));
        HTMLRequestMap.add(new ParagraphHTMLRequest("https://stackoverflow.com/jobs"));
        HTMLRequestMap.add(new ParagraphHTMLRequest("https://stackoverflow.com/tags"));
        HTMLRequestMap.add(new ParagraphHTMLRequest("https://stackoverflow.com/jobs/companies"));
        HTMLRequestMap.add(new ParagraphHTMLRequest("https://getpocket.com/explore/item/whoa-this-is-what-happens-to-your-body-when-you-drink-enough-water?utm_source=pocket-newtab"));
        HTMLRequestMap.add(new ParagraphHTMLRequest("https://hackernoon.com/need-faster-code-try-multithreading-5dc30c83837c"));
//        requestMap.add("https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java", new ParagraphRequest());
//        requestMap.add("https://stackoverflow.com/questions/7939257/wait-until-all-threads-finish-their-work-in-java", new ParagraphRequest());
//        requestMap.add("http://tutorials.jenkov.com/java-concurrency/concurrency-models.html", new ParagraphRequest());
//        requestMap.add("http://tutorials.jenkov.com/java-concurrency/benefits.html", new ParagraphRequest());
//        requestMap.add("http://tutorials.jenkov.com/java-concurrency/race-conditions-and-critical-sections.html", new ParagraphRequest());
//        requestMap.add("http://tutorials.jenkov.com/java-concurrency/threadlocal.html", new ParagraphRequest());
//        requestMap.add("http://tutorials.jenkov.com/java-concurrency/nested-monitor-lockout.html", new ParagraphRequest());

        TagHTMLRequestController requestHandler = TagHTMLRequestController.getHandler();
        requestHandler.multiRequest(HTMLRequestMap);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println("\n\n\n TIME: " + duration);

    }
}
