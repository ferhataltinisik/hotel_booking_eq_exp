Feature: booking test scenarios

  Background:
    Given I am on the Equalexperts homepage

  Scenario Outline: Create a booking for a specific date
    When I enter customer information "<Check_In_Date>" "<Check_Out_Dat>"
    And i submit the booking
    Then the booking is successfully stored
    Examples:
      | Check_In_Date | Check_Out_Dat |
      |  20 June 2022 |  27 June 2022 |


  Scenario: Create a booking for a specific day in advance
    When for after 5 days i wants make a booking for 7 nights
    And i submit the booking
    Then the booking is successfully stored


  Scenario Outline: Delete an individual booking successfully
    And I enter customer information "<Check_In_Date>" "<Check_Out_Dat>"
    And i submit the booking
    When i try to delete a booking from the booking list
    Then the booking is deleted successfully
    Examples:
      | Check_In_Date | Check_Out_Dat |
      |  20 June 2022 |  27 June 2022 |


  Scenario Outline: Create a booking by entering a negative amount for the price.
    When i try make a booking 5 days in advance for 7 nights with non-negative <price>
    And i submit the booking
    Then the booking is not stored

    Examples:
      | price |
      |  -20  |
      |   0   |


  Scenario: Booking should not be saved when any mandatory filed missing
    When i try to create a booking without mandatory filed
    And i submit the booking
    Then i verify warning messages