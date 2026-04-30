package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.AmazonHomePage;
import pages.AmazonSearchResultsPage;
import utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AmazonSearchSteps {

    private static final Logger log = LogManager.getLogger(AmazonSearchSteps.class);

    AmazonHomePage homePage = new AmazonHomePage();
    AmazonSearchResultsPage resultsPage = new AmazonSearchResultsPage();

    String product; //shared variable

    @Given("user opens Amazon homepage")
    public void openAmazon() {
        log.info("Amazon homepage opened");
    }

    @When("user enters product from json")
    public void enterSearch() {

        product = JsonReader.getProduct("src/test/resources/testdata/searchData.json");

        log.info("Entering product: " + product);
        homePage.enterSearchText(product);
    }
    @When("user enters {string} in search bar")
    public void enterSearch(String product) {

        this.product = product;   // store for later validation

        log.info("Entering product: " + product);
        homePage.enterSearchText(product);
    }

    @And("user clicks on first suggestion")
    public void clickFirstSuggestion() {
        log.info("Clicking first suggestion");
        homePage.clickFirstSuggestion();
    }

    @Then("results page should be displayed")
    public void verifyResultsPage() {
        log.info("Validating results page");
        Assert.assertTrue(resultsPage.isResultsPageDisplayed(),
                " Results page is NOT displayed");
    }

    @And("results should be relevant to entered product")
    public void validateResults() {

        log.info("Validating results for: " + product);

        Assert.assertTrue(resultsPage.verifyResultsContain(product),
                "Results are NOT relevant to: " + product);
    }
}