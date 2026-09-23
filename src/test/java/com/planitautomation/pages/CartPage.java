package com.planitautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.planitautomation.utils.CurrencyParser;

import java.math.BigDecimal;

/**
 * The shopping cart (http://jupiter.cloud.planittesting.com/#/cart). Each purchased
 * product is a "tr.cart-item" row with columns [name, price, quantity, subtotal,
 * actions]; the running total is rendered separately in an element carrying the
 * "total" class.
 */
public class CartPage extends BasePage {

    private static final int PRICE_COLUMN_INDEX = 1;
    private static final int SUBTOTAL_COLUMN_INDEX = 3;

    public CartPage(Page page) {
        super(page);
    }

    private Locator row(String productName) {
        return page.locator("tr.cart-item").filter(new Locator.FilterOptions().setHasText(productName));
    }

    public Locator priceCell(String productName) {
        return row(productName).locator("td").nth(PRICE_COLUMN_INDEX);
    }

    public Locator subtotalCell(String productName) {
        return row(productName).locator("td").nth(SUBTOTAL_COLUMN_INDEX);
    }

    public Locator total() {
        return page.locator(".total");
    }

    public BigDecimal getPrice(String productName) {
        return CurrencyParser.parse(priceCell(productName).textContent());
    }

    public BigDecimal getSubtotal(String productName) {
        return CurrencyParser.parse(subtotalCell(productName).textContent());
    }

    public BigDecimal getTotal() {
        return CurrencyParser.parse(total().textContent());
    }
}
