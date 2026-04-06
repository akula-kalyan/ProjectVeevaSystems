package pages;

import org.openqa.selenium.*;
import java.util.List;
import factory.DriverFactory;
import utils.WaitUtil;

public class CategoryPage {

    WebDriver driver = DriverFactory.getDriver();

    By sportOutdoor = By.xpath("//span[text()='Sport & Outdoor']");
    By toySportsEquipment = By.xpath("//span[text()='Toy Sports Equipment']");
    By football = By.xpath("//span[text()='Football']");
    By products = By.xpath("//a[contains(@class,'octopus-pc-item-link')]");

    // Navigate through categories
    public void navigateToFootball() {

        WaitUtil.click(driver, sportOutdoor);
        WaitUtil.click(driver, toySportsEquipment);
        WaitUtil.click(driver, football);
    }

    // Select first product
    public void selectProduct() {

        try {
            // Wait for at least ONE product to be visible
            WaitUtil.waitForElement(driver, products);

            // Now safely fetch list
            List<WebElement> list = driver.findElements(products);

            if (!list.isEmpty()) {

                WebElement firstProduct = list.get(0);

                // Scroll into view
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView(true);", firstProduct);

                // JS click (avoids interception issues)
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", firstProduct);

            } else {
                throw new RuntimeException(" No products found");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to select product: " + e.getMessage());
        }
    }
}