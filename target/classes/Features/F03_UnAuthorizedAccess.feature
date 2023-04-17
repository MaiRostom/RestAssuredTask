Feature: Foodics unAuthorized
  @smoke
  Scenario: unAuthorized login
    Given I have entered my  username and password
    When I make a POST request
    And Verify user details are retrieved successfully using access token
    Then Verify unauthorized access without access token

