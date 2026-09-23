package com.planitautomation.pages;

import com.microsoft.playwright.Page;

/**
 * Shared plumbing for every page object: holds the Playwright page handle and knows
 * how to build an absolute URL from the app's base URL and a route path.
 */
public abstract class BasePage {

    private static final String BASE_URL = "http://jupiter.cloud.planittesting.com";

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected static String url(String routePath) {
        return BASE_URL + routePath;
    }
}
