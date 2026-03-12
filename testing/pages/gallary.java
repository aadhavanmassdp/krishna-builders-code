package com.krishnabuilders.tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class GalleryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(GalleryPage.class.getName());

    @FindBy(id = "gallery-view")
    private WebElement galleryView;

    @FindBy(id = "gallery-title")
    private WebElement galleryTitle;

    @FindBy(id = "gallery-container")
    private WebElement galleryContainer;

    @FindBy(css = "#gallery-container .card")
    private List<WebElement> galleryItems;

    @FindBy(css = ".btn-outline-light")
    private WebElement backToHomeButton;

    @FindBy(css = ".badge")
    private List<WebElement> itemTags;

    public GalleryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public boolean isGalleryDisplayed() {
        try {
            return galleryView.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getGalleryTitle() {
        return wait.until(ExpectedConditions.visibilityOf(galleryTitle)).getText();
    }

    public int getGalleryItemCount() {
        return galleryItems.size();
    }

    public boolean areGalleryItemsDisplayed() {
        return !galleryItems.isEmpty() && galleryItems.stream().allMatch(WebElement::isDisplayed);
    }

    public void clickBackToHome() {
        wait.until(ExpectedConditions.elementToBeClickable(backToHomeButton)).click();
        logger.info("Clicked Back to Home button");
    }

    public String getFirstItemTag() {
        if (!itemTags.isEmpty()) {
            return itemTags.get(0).getText();
        }
        return "";
    }

    public void clickGalleryItem(int index) {
        if (index >= 0 && index < galleryItems.size()) {
            galleryItems.get(index).click();
            logger.info("Clicked gallery item at index: " + index);
        }
    }

    public boolean verifyImagesLoaded() {
        for (WebElement item : galleryItems) {
            WebElement img = item.findElement(By.tagName("img"));
            Boolean isLoaded = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].complete && " +
                            "typeof arguments[0].naturalWidth != 'undefined' && " +
                            "arguments[0].naturalWidth > 0", img);
            if (!isLoaded) {
                logger.warning("Image failed to load: " + img.getAttribute("src"));
                return false;
            }
        }
        return true;
    }
}
