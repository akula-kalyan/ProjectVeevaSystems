package hooks;

import factory.DriverFactory;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;
import utils.ScreenshotUtil;
import org.openqa.selenium.*;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) {

        log.info("========== TEST STARTED: " + scenario.getName() + " ==========");

        // Initialize driver
        DriverFactory.initDriver();

        // Always get driver from ThreadLocal
        WebDriver driver = DriverFactory.getDriver();

        // Open URL
        driver.get(ConfigReader.get("url"));

        log.info("Browser launched and URL opened | Thread: " + Thread.currentThread().getId());
    }

    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

        try {
            if (scenario.isFailed()) {

                log.error("TEST FAILED: " + scenario.getName());

                // Screenshot for report
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

                scenario.attach(screenshot, "image/png", scenario.getName());

                // Save locally
                ScreenshotUtil.capture(driver, scenario.getName());

                log.info("Screenshot captured");
            } else {
                log.info("TEST PASSED: " + scenario.getName());
            }

        } catch (Exception e) {
            log.error("Error in teardown: ", e);
        } finally {

            DriverFactory.quitDriver();

            log.info("Browser closed | Thread: " + Thread.currentThread().getId());
            log.info("========== TEST ENDED ==========\n");
        }
    }
}