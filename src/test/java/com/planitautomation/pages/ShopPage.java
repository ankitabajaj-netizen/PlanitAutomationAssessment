package com.planitautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * The product listing (http://jupiter.cloud.planittesting.com/#/shop). Each product is
 * rendered as an "li.product" card with its own "Buy" link; clicking "Buy" adds one
 * unit of that product to the cart without leaving the page.
 */
public class ShopPage extends BasePage {

    public ShopPage(Page page) {
        super(page);
    }

    private Locator product(String productName) {
        return page.locator("li.product").filter(new Locator.FilterOptions().setHasText(productName));
    }

    public Locator productPrice(String productName) {
        return product(productName).locator(".product-price");
    }

    private Locator buyButton(String productName) {
        return product(productName)
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Buy").setExact(true));
    }

    /** Reads the displayed price for a product, e.g. "$10.99". */
    public String getPriceText(String productName) {
        String text = productPrice(productName).textContent();
        return text == null ? "" : text.trim();
    }

    /** Clicks "Buy" for the given product the requested number of times. */
    public void buy(String productName, int quantity) {
        Locator button = buyButton(productName);
        for (int i = 0; i < quantity; i++) {
            button.click();
        }
    }
}
