#Feature: Login
#
#  Scenario: Successful login
#    Given I have entered my username and password
#    When I make a POST request to the login endpoint
#    Then I receive a response with status code 200
#    And the response body contains an access token
#
#  Scenario: Unauthorized access without login
#    When I make a GET request to the whoami endpoint
#    Then I receive a response with status code 401
#
#  Scenario: Authorized access after login
#    Given I have logged in successfully
#    When I make a GET request to the whoami endpoint with the access token
#    Then I receive a response with status code 200
#    And the response body contains my username
Feature: Foodics Login
@kk
Scenario: Successful login
  Given I have entered my username and password
  Then I make a POST request to the login endpoint
