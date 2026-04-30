package pages;

import org.openqa.selenium.*;
import factory.DriverFactory;
import utils.WaitUtil;

public class MenuPage {

    WebDriver driver = DriverFactory.getDriver();

    // Locators
    By allMenu = By.id("nav-hamburger-menu");
    By seeMore = By.xpath("//a[contains(@class,'hmenu-compressed-btn')]");
    By toysCategory = By.xpath("//a[@data-menu-id='15']");
    By toysAndGames = By.xpath("//a[@class='hmenu-item' and contains(text(),'Toys')]");
    By menuContainer = By.id("hmenu-content");

    public void navigateToToysGames() {

        // Click hamburger menu
        WaitUtil.click(driver, allMenu);

        // Wait for menu container
        WebElement menu = WaitUtil.waitForElement(driver, menuContainer);

        // Scroll menu
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollTop = arguments[0].scrollHeight", menu);

        // Click "See more" (optional)
        try {
            WaitUtil.click(driver, seeMore);
        } catch (Exception e) {
            System.out.println("See more not present");
        }

        // Scroll again
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollTop = arguments[0].scrollHeight", menu);

        // Wait for Toys category
        WebElement toys = WaitUtil.waitForElement(driver, toysCategory);

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", toys);

        // JS click (avoids interception)
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", toys);

        // Wait for Toys & Games option
        WebElement toysGames = WaitUtil.waitForElement(driver, toysAndGames);

        // JS click
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", toysGames);
    }
}