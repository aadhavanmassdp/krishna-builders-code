package com.krishnabuilders.tests.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class TestUtils {
    private static final Logger logger = Logger.getLogger(TestUtils.class.getName());

    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            logger.info("Screenshot saved: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            logger.severe("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static String generateRandomPhone() {
        return "9" + (int) (Math.random() * 1000000000);
    }

    public static String generateRandomName() {
        String[] names = {"John Doe", "Jane Smith", "Robert Johnson", "Maria Garcia", "David Brown"};
        return names[(int) (Math.random() * names.length)] + " " + System.currentTimeMillis() % 1000;
    }
}
