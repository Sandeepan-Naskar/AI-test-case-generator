Feature: Health Check
  As a DevOps engineer
  I want to verify the health status of the application
  So that I can ensure the service is operational and responsive

  Background:
    Given The application is running on "http://localhost:8080"

  Scenario: Happy Path - Application is Healthy
    When I call the health check endpoint at "/health"
    Then I should receive a status code of 200
    And The response body should be "OK"

  Scenario: Incorrect Endpoint - Nonexistent Endpoint
    When I call an incorrect endpoint at "/invalid"
    Then I should receive a status code of 404

  Scenario: Server Error - Application Not Running
    Given The application is not running
    When I call the health check endpoint at "/health"
    Then I should receive a status code of 500