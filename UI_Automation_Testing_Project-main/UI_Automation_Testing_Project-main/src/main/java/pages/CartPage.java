package pages;

import org.openqa.selenium.*;
import java.util.List;
import factory.DriverFactory;
import utils.WaitUtil;

public class CartPage {

    WebDriver driver;

    public CartPage() {
        this.driver = DriverFactory.getDriver();
    }

    // ================= LOCATORS =================

    By addedToCartMsg = By.xpath("//h1[contains(text(),'Added to Cart')]");

    By subtotalWhole = By.xpath("//span[@class='a-price-whole']");
    By subtotalContainer = By.id("sc-subtotal-amount-activecart");

    By cartCount = By.id("nav-cart-count");
    By cartButton = By.id("nav-cart");
    By cartItems = By.xpath("//div[contains(@class,'sc-list-item')]");

    // Product validation locators
    By cartProductTitle = By.xpath("//span[contains(@class,'sc-product-title')]");
    By cartProductPrice = By.xpath("//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']");

    public void goToCartPage() {
        try {
            WaitUtil.click(driver, cartButton);
        } catch (Exception e) {
            driver.get("https://www.amazon.in/gp/cart/view.html");
        }
    }

    // ================= VALIDATIONS =================

    public boolean isProductPresent() {
        try {
            List<WebElement> addedMsg = driver.findElements(addedToCartMsg);

            if (!addedMsg.isEmpty()) {
                return WaitUtil.waitForElement(driver, addedToCartMsg).isDisplayed();
            }

            return WaitUtil.waitForElement(driver, cartItems).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    // Get Cart Product Title
    public String getCartProductTitle() {
        return WaitUtil.waitForElement(driver, cartProductTitle)
                .getText().trim();
    }

    // Get Cart Product Price
    public String getCartProductPrice() {
        String price = WaitUtil.waitForElement(driver, cartProductPrice)
                .getText();

        return price.replaceAll("[^0-9.]", "")   // keep digits + dot
                .split("\\.")[0];            // remove decimal part
    }
    //returning the substring of requried length
    private String getSafeText(String text, int length) {
        if (text == null) return "";

        text = text.toLowerCase().trim();

        return text.substring(0, Math.min(length, text.length()));
    }
    // FINAL VALIDATION
    public boolean isCorrectProductAdded(String expectedName, String expectedPrice) {

        String actualName = getCartProductTitle();
        String actualPrice = getCartProductPrice();

        System.out.println("Expected Name: " + expectedName);
        System.out.println("Actual Name: " + actualName);

        System.out.println("Expected Price: " + expectedPrice);
        System.out.println("Actual Price: " + actualPrice);

        //take first 30 characters safely
        String expectedShort = getSafeText(expectedName, 30);
        String actualShort = getSafeText(actualName, 30);

        System.out.println("Expected (30 chars): " + expectedShort);
        System.out.println("Actual (30 chars): " + actualShort);

        boolean nameMatch = actualShort.equals(expectedShort);
        boolean priceMatch = actualPrice.equals(expectedPrice);

        return nameMatch && priceMatch;
    }

    public String getSubtotal() {
        try {
            if (!driver.findElements(subtotalContainer).isEmpty()) {
                return WaitUtil.waitForElement(driver, subtotalContainer).getText();
            }
            return WaitUtil.waitForElement(driver, subtotalWhole).getText();

        } catch (Exception e) {
            return "Subtotal not found";
        }
    }

    public String getCartCount() {
        try {
            return WaitUtil.waitForElement(driver, cartCount).getText();
        } catch (Exception e) {
            return "0";
        }
    }


}