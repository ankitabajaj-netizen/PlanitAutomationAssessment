package com.planitautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * The "Contact" feedback form (http://jupiter.cloud.planittesting.com/#/contact). */
public class ContactPage extends BasePage {

    public ContactPage(Page page) {
        super(page);
    }

    public Locator forenameInput() {
        return page.locator("#forename");
    }

    public Locator emailInput() {
        return page.locator("#email");
    }

    public Locator messageInput() {
        return page.locator("#message");
    }

    public Locator submitButton() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Submit"));
    }

    public Locator forenameError() {
        return page.locator("#forename-err");
    }

    public Locator emailError() {
        return page.locator("#email-err");
    }

    public Locator messageError() {
        return page.locator("#message-err");
    }

    /** Red banner summarising that the form could not be submitted. */
    public Locator validationSummary() {
        return page.locator("div.alert.alert-error");
    }

    /** Blue informational banner shown on a clean, unsubmitted form. */
    public Locator infoBanner() {
        return page.locator("div.alert.alert-info");
    }

    /** Green banner shown once the feedback has been accepted by the server. */
    public Locator successBanner() {
        return page.locator("div.alert.alert-success");
    }

    /** Fills only the three mandatory fields required for a valid submission. */
    public void fillMandatoryFields(String forename, String email, String message) {
        forenameInput().fill(forename);
        emailInput().fill(email);
        messageInput().fill(message);
    }

    public void submit() {
        submitButton().click();
    }

    /**
     * Waits for the success banner to appear and returns its trimmed tex */
     
    public String waitForSuccessMessage() {
        successBanner().waitFor(new Locator.WaitForOptions().setTimeout(20_000));
        String text = successBanner().textContent();
        return text == null ? "" : text.trim();
    }
}
