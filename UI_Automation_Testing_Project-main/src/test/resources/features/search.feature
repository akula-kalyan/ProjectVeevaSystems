Feature: Amazon Search Functionality

  Scenario: Search for iPhone using search bar
    Given user opens Amazon homepage
    When user enters "iPhone" in search bar
    And user clicks on first suggestion
    Then results page should be displayed
    And results should be relevant to "iPhone"