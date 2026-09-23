package com.planitautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * The Jupiter Toys landing page. Its only job in these tests is exposing the primary
 * navigation, and handing back the page object for wherever a link leads.
 */
public class HomePage extends BasePage {

    public HomePage(Page page) {
        super(page);
    }

    private Locator shopLink() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Shop").setExact(true));
    }

    private Locator contactLink() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Contact"));
    }

    // The cart link's accessible name includes the current item count, e.g. "Cart (0)",
    // so it is matched as a substring rather than an exact name.
    private Locator cartLink() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart"));
    }

    public void goTo() {
        page.navigate(url("/#/home"));
    }

    public ShopPage navigateToShop() {
        shopLink().click();
        return new ShopPage(page);
    }

    public ContactPage navigateToContact() {
        contactLink().click();
        return new ContactPage(page);
    }

    public CartPage navigateToCart() {
        cartLink().click();
        return new CartPage(page);
    }
}
