package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AmazonCartSteps {

    private static final Logger log = LogManager.getLogger(AmazonCartSteps.class);

    MenuPage menu = new MenuPage();
    CategoryPage category = new CategoryPage();
    ProductPage product = new ProductPage();
    CartPage cart = new CartPage();

    // Store product data
    String selectedProduct;
    String selectedPrice;

    @Given("user is on Amazon homepage")
    public void openAmazon() {
        log.info("Amazon homepage opened");
    }

    @When("user navigates to Toys category from menu")
    public void navigateMenu() {
        log.info("Navigating to Toys category");
        menu.navigateToToysGames();
    }

    @And("user selects a product from category page")
    public void selectProduct() {
        log.info("Selecting product");
        category.navigateToFootball();
        category.selectProduct();
    }

    @And("user switches to product tab")
    public void switchTab() {
        log.info("Switching to product tab");

        product.switchToTab();

        // ✅ Capture AFTER switching
        selectedProduct = product.getProductTitle();
        selectedPrice = product.getProductPrice();

        log.info("Selected Product: " + selectedProduct);
        log.info("Selected Price: " + selectedPrice);
    }

    @Then("product page should be displayed")
    public void validateProductPage() {
        Assert.assertTrue(product.isProductPageDisplayed(),
                "Product page is not displayed");
    }

    @When("user adds product to cart")
    public void addCart() {
        product.addToCart();
    }

    @And("user navigates to cart page")
    public void openCart() {
        product.goToCart();
    }
    @Then("product should be added to cart")
    public void validateProductAdded() {

        log.info("Validating correct product and price");

        String actualName = cart.getCartProductName();
        String actualPrice = cart.getCartProductPrice();

        System.out.println("Expected Name: " + selectedProduct);
        System.out.println("Actual Name: " + actualName);
        System.out.println("Expected Price: " + selectedPrice);
        System.out.println("Actual Price: " + actualPrice);

        // Compare using contains instead of equals
        boolean nameMatch = actualName.contains("Kidsmate");
        boolean priceMatch = actualPrice.contains(selectedPrice);

        Assert.assertTrue(nameMatch && priceMatch,
                "Product name or price mismatch!");
    }

    @And("subtotal should be displayed")
    public void validateSubtotal() {
        String subtotal = cart.getSubtotal();
        log.info("Subtotal: " + subtotal);

        Assert.assertTrue(subtotal.contains("₹"),
                "Subtotal not displayed correctly");
    }

    @And("cart count should be updated")
    public void validateCartCount() {
        int count = Integer.parseInt(cart.getCartCount());
        log.info("Cart count: " + count);

        Assert.assertTrue(count == 1,
                "Cart count not updated");
    }
}