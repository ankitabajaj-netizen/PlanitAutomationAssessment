# Test case 1 
Feature: Contact form validation
  As a site visitor
  I want the contact form to reject an incomplete submission
  So that the team never receives a message that's missing key details

  Background:
    Given I am on the Jupiter Toys home page
    When I navigate to the Contact page

  Scenario: Submitting an empty form shows validation errors that clear once corrected
    When I submit the contact form
    Then I should see validation errors for the mandatory fields
    When I populate the mandatory fields with forename "John", email "john.doe@example.com" and message "This is a test message"
    Then the validation errors should no longer be displayed
