package pages;

import org.openqa.selenium.*;
import java.util.Set;
import factory.DriverFactory;
import utils.WaitUtil;

public class ProductPage {

    WebDriver driver = DriverFactory.getDriver();

    By productTitle = By.id("productTitle");

    //Using YOUR working locator (stable)
    By productPrice = By.xpath("//span[@class='a-price-whole']");

    By addToCart = By.id("add-to-cart-button");
    By goToCartBtn = By.xpath("//a[contains(text(),'Go to Cart')]");

    // Switch to new tab
    public void switchToTab() {
        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            driver.switchTo().window(handle);
        }
    }

    // Get Product Title
    public String getProductTitle() {
        return WaitUtil.waitForElement(driver, productTitle)
                .getText().trim();
    }

    // Get Product Price
    public String getProductPrice() {
        String price = WaitUtil.waitForElement(driver, productPrice)
                .getText();   // 499

        return price.replaceAll("[^0-9]", "");
    }

    // Validate product page
    public boolean isProductPageDisplayed() {
        try {
            return WaitUtil.waitForElement(driver, productTitle).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Click Add to Cart
    public void addToCart() {
        WaitUtil.click(driver, addToCart);
    }

    // Navigate to Cart
    public void goToCart() {
        try {
            WaitUtil.click(driver, goToCartBtn);
        } catch (Exception e) {
            driver.get("https://www.amazon.in/gp/cart/view.html");
        }
    }
}