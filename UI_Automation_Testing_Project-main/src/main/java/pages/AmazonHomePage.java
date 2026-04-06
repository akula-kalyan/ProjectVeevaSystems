package pages;

import org.openqa.selenium.*;
import java.util.List;
import factory.DriverFactory;
import utils.WaitUtil;

public class AmazonHomePage {

    WebDriver driver = DriverFactory.getDriver();

    By searchBox = By.id("twotabsearchtextbox");
    By suggestions = By.xpath("//div[@role='button' and contains(@class,'s-suggestion')]");

    // Enter text in search box
    public void enterSearchText(String text) {
        WaitUtil.sendKeys(driver, searchBox, text);
    }

    // Click first suggestion or press Enter
    public void clickFirstSuggestion() {

        try {
            List<WebElement> list = WaitUtil.waitForElements(driver, suggestions);

            if (!list.isEmpty()) {
                list.get(0).click();
            } else {
                driver.findElement(searchBox).sendKeys(Keys.ENTER);
            }

        } catch (Exception e) {
            driver.findElement(searchBox).sendKeys(Keys.ENTER);
        }
    }
}