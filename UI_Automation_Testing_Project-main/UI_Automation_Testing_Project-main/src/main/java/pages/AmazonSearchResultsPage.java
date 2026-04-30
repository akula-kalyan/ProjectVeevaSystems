package pages;

import org.openqa.selenium.*;
import java.util.List;
import factory.DriverFactory;
import utils.WaitUtil;

public class AmazonSearchResultsPage {

    WebDriver driver = DriverFactory.getDriver();

    By results = By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']");

    // Check if results page is displayed
    public boolean isResultsPageDisplayed() {
        try {
            return WaitUtil.waitForElement(driver, results).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Verify if results contain expected product
    public boolean verifyResultsContain(String product) {

        try {
            List<WebElement> list = WaitUtil.waitForElements(driver, results);

            for (WebElement e : list) {
                if (e.getText().toLowerCase().contains(product.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}