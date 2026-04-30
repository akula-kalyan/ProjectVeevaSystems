package hooks;

import factory.DriverFactory;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;
import utils.ScreenshotUtil;
import utils.ExtentManager;

import org.openqa.selenium.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;

import java.util.Base64;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    // Extent Report objects
    private static ExtentReports extent = ExtentManager.getInstance();
    public static ExtentTest test;

    @Before
    public void setup(Scenario scenario) {

        log.info("========== TEST STARTED: " + scenario.getName() + " ==========");

        // Create test in Extent Report
        test = extent.createTest(scenario.getName());

        // Initialize driver
        DriverFactory.initDriver();

        WebDriver driver = DriverFactory.getDriver();

        driver.get(ConfigReader.get("url"));

        log.info("Browser launched and URL opened | Thread: " + Thread.currentThread().getId());

        test.info("Browser launched and URL opened");
    }

    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

        try {
            if (scenario.isFailed()) {

                log.error("TEST FAILED: " + scenario.getName());

                // Screenshot for cucumber report
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

                scenario.attach(screenshot, "image/png", scenario.getName());

                // Save locally
                ScreenshotUtil.capture(driver, scenario.getName());

                // Convert screenshot to Base64 for Extent
                String base64 = Base64.getEncoder().encodeToString(screenshot);

                //  Attach screenshot in Extent report
                test.fail("Test Failed: " + scenario.getName(),
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());

                log.info("Screenshot captured");

            } else {
                log.info("TEST PASSED: " + scenario.getName());

                // Mark PASS in Extent
                test.pass("Test Passed: " + scenario.getName());
            }

        } catch (Exception e) {
            log.error("Error in teardown: ", e);
            test.fail("Exception: " + e.getMessage());
        } finally {

            DriverFactory.quitDriver();

            log.info("Browser closed | Thread: " + Thread.currentThread().getId());
            log.info("========== TEST ENDED ==========\n");

            // Generate report
            extent.flush();
        }
    }
}