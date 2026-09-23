package com.planitautomation.stepdefinitions;

import com.planitautomation.support.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Steps for test cases 1 and 2: contact form validation and submission. */
public class ContactFormSteps {

    private static final String EXPECTED_VALIDATION_SUMMARY_TEXT =
            "We welcome your feedback - but we won't get it unless you complete the form correctly.";

    private static final String EXPECTED_INFO_BANNER_TEXT =
            "We welcome your feedback - tell it how it is.";

    private final TestContext testContext;

    public ContactFormSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I submit the contact form")
    public void iSubmitTheContactForm() {
        testContext.contactPage.submit();
    }

    @When("I populate the mandatory fields with forename {string}, email {string} and message {string}")
    public void iPopulateTheMandatoryFields(String forename, String email, String message) {
        testContext.contactPage.fillMandatoryFields(forename, email, message);
    }

    @Then("I should see validation errors for the mandatory fields")
    public void iShouldSeeValidationErrors() {
        assertThat(testContext.contactPage.validationSummary()).isVisible();
        assertThat(testContext.contactPage.validationSummary()).hasText(EXPECTED_VALIDATION_SUMMARY_TEXT);
        assertThat(testContext.contactPage.forenameError()).isVisible();
        assertThat(testContext.contactPage.emailError()).isVisible();
        assertThat(testContext.contactPage.messageError()).isVisible();
    }

    @Then("the validation errors should no longer be displayed")
    public void theValidationErrorsShouldNoLongerBeDisplayed() {
        assertThat(testContext.contactPage.validationSummary()).isHidden();
        assertThat(testContext.contactPage.forenameError()).isHidden();
        assertThat(testContext.contactPage.emailError()).isHidden();
        assertThat(testContext.contactPage.messageError()).isHidden();
        assertThat(testContext.contactPage.infoBanner()).isVisible();
        assertThat(testContext.contactPage.infoBanner()).hasText(EXPECTED_INFO_BANNER_TEXT);
    }

    @Then("I should see the successful submission message for {string}")
    public void iShouldSeeTheSuccessfulSubmissionMessage(String forename) {
        String message = testContext.contactPage.waitForSuccessMessage();
        assertEquals("Thanks " + forename + ", we appreciate your feedback.", message);
    }
}
