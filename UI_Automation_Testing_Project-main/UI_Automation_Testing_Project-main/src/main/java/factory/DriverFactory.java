package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Initialize Driver
    public static void initDriver() {

        String browser = ConfigReader.get("browser");
        System.out.println("Launching browser: " + browser);
        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-blink-features=AutomationControlled");

            driver.set(new ChromeDriver(options));
        } else if (browser.equalsIgnoreCase("edge")) {

            System.setProperty("webdriver.edge.driver", "C:\\Drivers\\msedgedriver.exe");

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-blink-features=AutomationControlled");

            driver.set(new EdgeDriver(options));
        } else {
            throw new RuntimeException("Browser not supported: " + browser);
        }

        // Common setup
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();

    }
    // Get Driver
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Quit Driver
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}