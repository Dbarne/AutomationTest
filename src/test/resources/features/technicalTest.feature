@all
@technicalTest
Feature: Run a technical test

  @checkOut
  Scenario Outline: Log in to SwagLabs, Add Item to basket, Checkout
    Given I have opened the SwagLabs login page
    And I login to SwagLab with valid credentials
    And I validate the Product page
    And I select an "<item>" from the Product page
    When I add "<item>" the to the cart
    And I Navigate to the shopping cart
    And I validate the "<item>" in Shopping Cart
    And I enter checkout credentials
    And I validate the "<item>" checkout details
    Then I verify order has been completed
    Examples:
      | item                  |
      | Sauce Labs Bike Light |