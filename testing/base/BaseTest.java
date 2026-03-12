package com.krishnabuilders.tests.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    protected static final String BASE_URL = "file:///" + System.getProperty("user.dir") + "/src/main/resources/index.html";
    // If hosted online, use: protected static final String BASE_URL = "http://localhost:5500/index.html";

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        logger.info("Initializing WebDriver for browser: " + browser);
        
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-notifications");
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;
            case "edge":
                driver = new EdgeDriver();
                driver.manage().window().maximize();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        logger.info("WebDriver initialized successfully");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
        }
    }

    protected void navigateToHome() {
        driver.get(BASE_URL);
        logger.info("Navigated to: " + BASE_URL);
    }
}
