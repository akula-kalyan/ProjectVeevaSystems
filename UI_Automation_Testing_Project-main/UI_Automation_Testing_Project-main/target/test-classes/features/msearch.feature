Feature: Amazon Search Functionality

  Scenario Outline: Search product using multiple data
    Given user opens Amazon homepage
    When user enters "<product>" in search bar
    And user clicks on first suggestion
    Then results page should be displayed
    And results should be relevant to entered product

    Examples:
      | product  |
      | iPhone   |
      | Samsung  |
      | Laptop   |