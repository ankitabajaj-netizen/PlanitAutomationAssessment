# Test case 3
Feature: Shopping cart
  As a shopper
  I want the cart to price my items correctly
  So that I'm confident about what I'll be charged before checking out

  Scenario: Cart shows correct prices, subtotals and total for the purchased products
    Given I am on the Jupiter Toys home page
    When I go to the shop page
    And I buy 2 "Stuffed Frog"
    And I buy 5 "Fluffy Bunny"
    And I buy 3 "Valentine Bear"
    And I go to the cart page
    Then the price and subtotal for each purchased product should be correct
    And the cart total should equal the sum of the product subtotals
