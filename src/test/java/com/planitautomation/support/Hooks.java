package com.planitautomation.support;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/**
 * Scenario lifecycle: start a fresh, isolated headless Chromium context before every
 * scenario and tear it down afterwards. Cucumber-JVM injects the scenario-scoped
 * {@link TestContext} automatically via constructor injection (cucumber-picocontainer).
 */
public class Hooks {

    private final TestContext testContext;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void startBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        browserContext = browser.newContext();
        testContext.page = browserContext.newPage();
    }

    @After
    public void stopBrowser() {
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
