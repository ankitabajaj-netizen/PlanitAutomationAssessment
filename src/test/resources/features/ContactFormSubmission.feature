# Test case 2 
Feature: Contact form submission
  As a site visitor
  I want my feedback to be accepted when I fill in the contact form correctly
  So that I know my message actually reached the team

  Background:
    Given I am on the Jupiter Toys home page
    When I navigate to the Contact page
# Five independent runs 

  Scenario Outline: Submitting a fully populated form succeeds
    When I populate the mandatory fields with forename "<forename>", email "<email>" and message "<message>"
    And I submit the contact form
    Then I should see the successful submission message for "<forename>"

    Examples:
      | forename | email                       | message                           |
      | Ankita   | ankita.tester.1@example.com | Automated contact form run 1 of 5 |
      | Ankita   | ankita.tester.2@example.com | Automated contact form run 2 of 5 |
      | Ankita   | ankita.tester.3@example.com | Automated contact form run 3 of 5 |
      | Ankita   | ankita.tester.4@example.com | Automated contact form run 4 of 5 |
      | Ankita   | ankita.tester.5@example.com | Automated contact form run 5 of 5 |
