package com.krishnabuilders.tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(HomePage.class.getName());

    // Locators using @FindBy
    @FindBy(css = ".navbar-brand")
    private WebElement navbarBrand;

    @FindBy(className = "navbar-logo")
    private WebElement navbarLogo;

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    @FindBy(linkText = "About")
    private WebElement aboutLink;

    @FindBy(linkText = "Services")
    private WebElement servicesLink;

    @FindBy(linkText = "Contact")
    private WebElement contactLink;

    @FindBy(css = "#home .hero")
    private WebElement heroSection;

    @FindBy(css = "#home h1")
    private WebElement heroTitle;

    @FindBy(css = ".btn-royal")
    private List<WebElement> royalButtons;

    @FindBy(css = ".btn-outline-light")
    private WebElement viewWorksButton;

    @FindBy(id = "about")
    private WebElement aboutSection;

    @FindBy(css = ".about-img")
    private WebElement aboutImage;

    @FindBy(css = ".admin-thumb")
    private WebElement adminThumb;

    @FindBy(id = "services")
    private WebElement servicesSection;

    @FindBy(css = ".service-card")
    private List<WebElement> serviceCards;

    @FindBy(id = "contact")
    private WebElement contactSection;

    @FindBy(className = "whatsapp-float")
    private WebElement whatsappButton;

    @FindBy(id = "contactForm")
    private WebElement contactForm;

    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(name = "message")
    private WebElement messageInput;

    @FindBy(css = "#contactForm button[type='submit']")
    private WebElement submitButton;

    @FindBy(id = "formStatus")
    private WebElement formStatus;

    @FindBy(css = ".navbar-toggler")
    private WebElement mobileMenuToggler;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Navigation Methods
    public void clickHomeLink() {
        wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
        logger.info("Clicked Home link");
    }

    public void clickAboutLink() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click();
        logger.info("Clicked About link");
    }

    public void clickServicesLink() {
        wait.until(ExpectedConditions.elementToBeClickable(servicesLink)).click();
        logger.info("Clicked Services link");
    }

    public void clickContactLink() {
        wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();
        logger.info("Clicked Contact link");
    }

    public void clickViewWorksButton() {
        wait.until(ExpectedConditions.elementToBeClickable(viewWorksButton)).click();
        logger.info("Clicked View Works button");
    }

    public void clickWhatsAppButton() {
        wait.until(ExpectedConditions.elementToBeClickable(whatsappButton)).click();
        logger.info("Clicked WhatsApp button");
    }

    public void clickServiceCard(int index) {
        if (index >= 0 && index < serviceCards.size()) {
            wait.until(ExpectedConditions.elementToBeClickable(serviceCards.get(index))).click();
            logger.info("Clicked service card at index: " + index);
        } else {
            throw new IndexOutOfBoundsException("Invalid service card index: " + index);
        }
    }

    public void openMobileMenu() {
        if (mobileMenuToggler.isDisplayed()) {
            mobileMenuToggler.click();
            logger.info("Opened mobile menu");
        }
    }

    // Form Methods
    public void fillContactForm(String name, String phone, String message) {
        logger.info("Filling contact form - Name: " + name + ", Phone: " + phone);
        wait.until(ExpectedConditions.visibilityOf(nameInput)).sendKeys(name);
        phoneInput.sendKeys(phone);
        messageInput.sendKeys(message);
    }

    public void submitContactForm() {
        submitButton.click();
        logger.info("Submitted contact form");
    }

    public String getFormStatus() {
        try {
            wait.until(ExpectedConditions.visibilityOf(formStatus));
            return formStatus.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean isFormStatusDisplayed() {
        try {
            return formStatus.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Verification Methods
    public boolean isHeroSectionDisplayed() {
        return heroSection.isDisplayed();
    }

    public boolean isAboutSectionDisplayed() {
        return aboutSection.isDisplayed();
    }

    public boolean isServicesSectionDisplayed() {
        return servicesSection.isDisplayed();
    }

    public boolean isContactSectionDisplayed() {
        return contactSection.isDisplayed();
    }

    public String getHeroTitle() {
        return heroTitle.getText();
    }

    public String getNavbarBrandText() {
        return navbarBrand.getText();
    }

    public boolean isNavbarLogoDisplayed() {
        return navbarLogo.isDisplayed();
    }

    public boolean isAdminThumbDisplayed() {
        return adminThumb.isDisplayed();
    }

    public int getServiceCardCount() {
        return serviceCards.size();
    }

    public boolean areAllServiceCardsDisplayed() {
        return serviceCards.stream().allMatch(WebElement::isDisplayed);
    }

    public boolean isContactFormDisplayed() {
        return contactForm.isDisplayed();
    }

    public boolean areFormFieldsDisplayed() {
        return nameInput.isDisplayed() && phoneInput.isDisplayed() && messageInput.isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void scrollToAboutSection() {
        scrollToElement(aboutSection);
    }

    public void scrollToServicesSection() {
        scrollToElement(servicesSection);
    }

    public void scrollToContactSection() {
        scrollToElement(contactSection);
    }

    public boolean verifyFormValidation() {
        // Clear form and submit empty
        nameInput.clear();
        phoneInput.clear();
        messageInput.clear();
        submitButton.click();

        // Check HTML5 validation
        String validationMessage = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", nameInput);
        return validationMessage != null && !validationMessage.isEmpty();
    }
}
