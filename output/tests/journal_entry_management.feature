Feature: Journal Entry Management
  As an application user
  I want to manage journal entries
  So that I can create, read, update, and delete entries

  Background:
    Given the system is initialized with a clean database

  Scenario: Create a new journal entry with valid data
    When I send a POST request to "/api/journal" with the following JSON:
      """
      {
        "title": "First Entry",
        "content": "This is my first journal entry",
        "date": "2023-01-01T12:00:00"
      }
      """
    Then the response status should be 201
    And the response should contain:
      """
      {
        "title": "First Entry",
        "content": "This is my first journal entry",
        "date": "2023-01-01T12:00:00"
      }
      """

  Scenario: Retrieve all journal entries
    When I send a GET request to "/api/journal"
    Then the response status should be 200
    And the response should be an array of journal entries
    And the array should not be empty

  Scenario: Update an existing journal entry
    Given there exists a journal entry with:
      """
      {
        "title": "First Entry",
        "content": "This is my first journal entry",
        "date": "2023-01-01T12:00:00"
      }
      """
    When I send a PUT request to "/api/journal/{id}" with the following JSON:
      """
      {
        "title": "Updated Entry",
        "content": "This is the updated journal entry",
        "date": "2023-01-02T13:00:00"
      }
      """
    Then the response status should be 200
    And the response should contain:
      """
      {
        "title": "Updated Entry",
        "content": "This is the updated journal entry",
        "date": "2023-01-02T13:00:00"
      }
      """

  Scenario: Delete a journal entry
    Given there exists a journal entry with id "{id}"
    When I send a DELETE request to "/api/journal/{id}"
    Then the response status should be 204
    And the response body should be empty

  Scenario: Attempt to retrieve a non-existent journal entry
    When I send a GET request to "/api/journal/nonExistentId"
    Then the response status should be 404
    And the response should contain "Journal entry not found with id: nonExistentId"

  Scenario: Attempt to delete a non-existent journal entry
    When I send a DELETE request to "/api/journal/nonExistentId"
    Then the response status should be 404
    And the response should contain "Journal entry not found with id: nonExistentId"

  Scenario Outline: Invalid request body for creating journal entry
    When I send a POST request to "/api/journal" with the following JSON:
      """
      {
        "title": "<title>",
        "content": "<content>"
      }
      """
    Then the response status should be <status>
    And the response should contain "<message>"

    Examples:
      | title          | content                     | status | message                           |
      |                | "Missing title"             | 400    | "title is required"               |
      | "Test Title"   |                           | 400    | "content is required"             |
      |                |                           | 400    | "title is required"               |