package com.planitautomation.stepdefinitions;

import com.planitautomation.support.TestContext;
import com.planitautomation.utils.CurrencyParser;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Steps for test case 3: buying products and verifying the cart's prices, subtotals
 * and total. Prices are recorded from the shop page at purchase time (rather than
 * hard-coded), so the later assertions keep validating the real business rules -
 * subtotal = price x quantity, total = sum(subtotals) - even if the catalogue's
 * prices change.
 */
public class ShoppingCartSteps {

    private record Purchase(int quantity, BigDecimal price) {
    }

    private final TestContext testContext;
    private final Map<String, Purchase> purchases = new LinkedHashMap<>();

    public ShoppingCartSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I buy {int} {string}")
    public void iBuy(int quantity, String productName) {
        BigDecimal price = CurrencyParser.parse(testContext.shopPage.getPriceText(productName));
        purchases.put(productName, new Purchase(quantity, price));
        testContext.shopPage.buy(productName, quantity);
    }

    @Then("the price and subtotal for each purchased product should be correct")
    public void thePriceAndSubtotalShouldBeCorrect() {
        for (Map.Entry<String, Purchase> entry : purchases.entrySet()) {
            String productName = entry.getKey();
            Purchase purchase = entry.getValue();

            BigDecimal actualPrice = testContext.cartPage.getPrice(productName);
            BigDecimal actualSubtotal = testContext.cartPage.getSubtotal(productName);
            BigDecimal expectedSubtotal = purchase.price().multiply(BigDecimal.valueOf(purchase.quantity()));

            assertEquals(0, purchase.price().compareTo(actualPrice),
                    "Cart price for '" + productName + "' did not match the price shown on the shop page.");

            assertEquals(0, expectedSubtotal.compareTo(actualSubtotal),
                    "Subtotal for '" + productName + "' should equal price (" + purchase.price()
                            + ") x quantity (" + purchase.quantity() + ").");
        }
    }

    @Then("the cart total should equal the sum of the product subtotals")
    public void theCartTotalShouldEqualTheSum() {
        BigDecimal expectedTotal = BigDecimal.ZERO;
        for (String productName : purchases.keySet()) {
            expectedTotal = expectedTotal.add(testContext.cartPage.getSubtotal(productName));
        }

        BigDecimal actualTotal = testContext.cartPage.getTotal();

        assertEquals(0, expectedTotal.compareTo(actualTotal),
                "Cart total should equal the sum of every product's subtotal.");
    }
}
