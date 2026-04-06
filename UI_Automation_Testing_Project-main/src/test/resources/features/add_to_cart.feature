Feature: Amazon Add to Cart Functionality

  Scenario: Add product to cart from Toys category

    Given user is on Amazon homepage
    When user navigates to Toys category from menu
    And user selects a product from category page
    And user switches to product tab
    Then product page should be displayed
    When user adds product to cart
    And user navigates to cart page
    Then product should be added to cart
    And subtotal should be displayed
    And cart count should be updated