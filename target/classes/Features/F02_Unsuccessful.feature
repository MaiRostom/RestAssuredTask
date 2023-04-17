Feature: Foodics Login
  @smoke
  Scenario: UnSuccessful login
    Given I have entered my wrong username and password
    Then I make a POST request to the login endpoint to check
