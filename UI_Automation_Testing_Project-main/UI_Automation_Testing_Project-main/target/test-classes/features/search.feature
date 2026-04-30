Feature: Amazon Search Functionality

  Scenario: Search product using JSON data

    Given user opens Amazon homepage
    When user enters product from json
    And user clicks on first suggestion
    Then results page should be displayed
    And results should be relevant to entered product