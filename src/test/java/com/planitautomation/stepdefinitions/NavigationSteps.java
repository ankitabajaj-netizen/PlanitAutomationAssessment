package com.planitautomation.stepdefinitions;

import com.planitautomation.pages.HomePage;
import com.planitautomation.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

/** Steps for moving between the app's top-level pages. */
public class NavigationSteps {

    private final TestContext testContext;

    public NavigationSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I am on the Jupiter Toys home page")
    public void iAmOnTheHomePage() {
        HomePage homePage = new HomePage(testContext.page);
        homePage.goTo();
        testContext.homePage = homePage;
    }

    @When("I navigate to the Contact page")
    public void iNavigateToTheContactPage() {
        testContext.contactPage = testContext.homePage.navigateToContact();
    }

    @When("I go to the shop page")
    public void iGoToTheShopPage() {
        testContext.shopPage = testContext.homePage.navigateToShop();
    }

    @When("I go to the cart page")
    public void iGoToTheCartPage() {
        testContext.cartPage = testContext.homePage.navigateToCart();
    }
}
