package com.planitautomation.support;

import com.microsoft.playwright.Page;
import com.planitautomation.pages.CartPage;
import com.planitautomation.pages.ContactPage;
import com.planitautomation.pages.HomePage;
import com.planitautomation.pages.ShopPage;

/**
 * Scenario-scoped state. Cucumber-JVM's PicoContainer integration creates one instance
 * of this class per scenario and injects that same instance into every hook and
 * step-definition class that asks for it via its constructor - that's how gets hold of the same ContactPage that NavigationSteps
 * navigated to a moment earlier in the same scenario.
 */
public class TestContext {

    public Page page;

    public HomePage homePage;
    public ContactPage contactPage;
    public ShopPage shopPage;
    public CartPage cartPage;
}
