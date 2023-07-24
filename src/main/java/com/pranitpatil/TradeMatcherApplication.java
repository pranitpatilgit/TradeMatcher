package com.pranitpatil;

import com.pranitpatil.service.ApplicationExecutorService;
import com.pranitpatil.service.ApplicationExecutorServiceImpl;

import java.io.InputStream;

public class TradeMatcherApplication {

    private ApplicationExecutorService applicationExecutorService;

    public TradeMatcherApplication(InputStream inputStream) {
        this.applicationExecutorService = new ApplicationExecutorServiceImpl(inputStream);
    }

    public static void main(String[] args) {
        TradeMatcherApplication application = new TradeMatcherApplication(System.in);
        application.applicationExecutorService.startApplication();
    }
}
