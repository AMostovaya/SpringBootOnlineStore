package ru.geekbrains.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.geekbrains.DriverInitializer;

import static org.assertj.core.api.Assertions.assertThat;

public class AddCategorySteps {

    private WebDriver webDriver = null;

    @Given("^I open web browser$")
    public void iOpenFirefoxBrowser() throws Throwable {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to login page$")
    public void iNavigateToLoginHtmlPage() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("login.url"));
    }

    @When("^I click on login button$")
    public void iClickOnLoginButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-login"));
        webElement.click();
    }

    @When("^I provide username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void iProvideUsernameAsAndPasswordAs(String username, String password) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("inp-username"));
        webElement.sendKeys(username);
        Thread.sleep(2000);
        webElement = webDriver.findElement(By.id("inp-password"));
        webElement.sendKeys(password);
        Thread.sleep(2000);
    }

    @Then("^name should be \"([^\"]*)\"$")
    public void nameShouldBe(String name) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("dd_user"));
        assertThat(webElement.getText()).isEqualTo(name);
    }

    @Then("I go to categories page")
    public void iGoToCategoriesPage() throws InterruptedException {
        webDriver.get(DriverInitializer.getProperty("categories.url"));
        Thread.sleep(2000);
    }

    @When("I click on Add category button")
    public void iClickOnAddCategoryButton() {
        WebElement webElement = webDriver.findElement(By.linkText("Add Category"));
        webElement.click();
    }

    @Then("Go to category form")
    public void goToCategoryForm() {
        webDriver.get(DriverInitializer.getProperty("category.url"));
    }

    @And("I fill category as {string}")
    public void iFillCategoryAs(String category) throws InterruptedException {
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(category);
        Thread.sleep(2000);
    }

    @And("I click on submit button")
    public void iClickOnSubmitButton() {
        WebElement webElement = webDriver.findElement(By.tagName("button"));
        webElement.submit();
    }

    @Then("I return to categories page")
    public void iReturnToCategoriesPage() throws InterruptedException {
        webDriver.get(DriverInitializer.getProperty("categories.url"));
        Thread.sleep(2000);
    }
}
