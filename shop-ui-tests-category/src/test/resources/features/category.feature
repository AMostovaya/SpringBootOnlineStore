Feature: AddCategory
  Scenario Outline: Successful login to the page and add new category
    Given I open web browser
    When I navigate to login page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    Then name should be "<name>"
    Then I go to categories page
    When I click on Add category button
    Then Go to category form
    And I fill category as "<category>"
    And I click on submit button
    Then I return to categories page

    Examples:
      | username | password | name | category |
      | admin | admin | admin | new category  |
