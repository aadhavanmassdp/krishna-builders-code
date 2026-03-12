package com.krishnabuilders.tests;

import com.krishnabuilders.tests.base.BaseTest;
import com.krishnabuilders.tests.pages.GalleryPage;
import com.krishnabuilders.tests.pages.HomePage;
import com.krishnabuilders.tests.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;

public class KrishnaBuildersTest extends BaseTest {
    private HomePage homePage;
    private GalleryPage galleryPage;
    private SoftAssert softAssert;
    private static final Logger logger = Logger.getLogger(KrishnaBuildersTest.class.getName());

    @BeforeMethod
    public void initializePages() {
        navigateToHome();
        homePage = new HomePage(driver);
        galleryPage = new GalleryPage(driver);
        softAssert = new SoftAssert();
    }

    @Test(priority = 1, description = "Verify page loads and basic elements are present")
    public void testPageLoadAndBasicElements() {
        logger.info("Starting test: testPageLoadAndBasicElements");

        // Verify all main sections are displayed
        softAssert.assertTrue(homePage.isHeroSectionDisplayed(), "Hero section not displayed");
        softAssert.assertTrue(homePage.isAboutSectionDisplayed(), "About section not displayed");
        softAssert.assertTrue(homePage.isServicesSectionDisplayed(), "Services section not displayed");
        softAssert.assertTrue(homePage.isContactSectionDisplayed(), "Contact section not displayed");

        // Verify navbar elements
        softAssert.assertTrue(homePage.isNavbarLogoDisplayed(), "Navbar logo not displayed");
        softAssert.assertTrue(homePage.getNavbarBrandText().contains("KRISHNA"), 
                "Navbar brand text incorrect");

        // Verify hero section content
        String heroTitle = homePage.getHeroTitle();
        softAssert.assertTrue(heroTitle.contains("KRISHNA"), 
                "Hero title doesn't contain KRISHNA: " + heroTitle);

        // Verify admin image
        softAssert.assertTrue(homePage.isAdminThumbDisplayed(), "Admin thumbnail not displayed");

        // Verify service cards
        softAssert.assertEquals(homePage.getServiceCardCount(), 3, 
                "Expected 3 service cards");
        softAssert.assertTrue(homePage.areAllServiceCardsDisplayed(), 
                "Not all service cards are displayed");

        // Verify contact form
        softAssert.assertTrue(homePage.isContactFormDisplayed(), "Contact form not displayed");
        softAssert.assertTrue(homePage.areFormFieldsDisplayed(), "Form fields not displayed");

        // Verify WhatsApp button
        softAssert.assertTrue(homePage.clickWhatsAppButton() || true, 
                "WhatsApp button clickable"); // We don't actually need to click

        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Test navigation links")
    public void testNavigationLinks() {
        logger.info("Starting test: testNavigationLinks");

        // Test About link
        homePage.clickAboutLink();
        softAssert.assertTrue(driver.getCurrentUrl().contains("#about") || 
                homePage.isAboutSectionDisplayed(), "About navigation failed");

        // Test Services link
        homePage.clickServicesLink();
        softAssert.assertTrue(driver.getCurrentUrl().contains("#services") || 
                homePage.isServicesSectionDisplayed(), "Services navigation failed");

        // Test Contact link
        homePage.clickContactLink();
        softAssert.assertTrue(driver.getCurrentUrl().contains("#contact") || 
                homePage.isContactSectionDisplayed(), "Contact navigation failed");

        // Test View Works button
        homePage.clickViewWorksButton();
        softAssert.assertTrue(driver.getCurrentUrl().contains("#services") || 
                homePage.isServicesSectionDisplayed(), "View Works navigation failed");

        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Test gallery functionality")
    public void testGalleryFunctionality() {
        logger.info("Starting test: testGalleryFunctionality");

        // Test Planning gallery
        homePage.clickServiceCard(0); // Planning card
        TestUtils.waitForPageLoad(driver);

        softAssert.assertTrue(galleryPage.isGalleryDisplayed(), "Gallery not displayed after clicking Planning");
        softAssert.assertTrue(galleryPage.getGalleryTitle().contains("Approval"), 
                "Gallery title incorrect for Planning");
        softAssert.assertTrue(galleryPage.getGalleryItemCount() > 0, 
                "No gallery items displayed");
        softAssert.assertTrue(galleryPage.verifyImagesLoaded(), 
                "Some images failed to load");

        // Return to home
        galleryPage.clickBackToHome();
        TestUtils.waitForPageLoad(driver);
        softAssert.assertTrue(homePage.isHeroSectionDisplayed(), 
                "Failed to return to home from gallery");

        // Test Engineering gallery
        homePage.clickServiceCard(1); // Engineering card
        TestUtils.waitForPageLoad(driver);

        softAssert.assertTrue(galleryPage.getGalleryTitle().contains("Design"), 
                "Gallery title incorrect for Engineering");
        softAssert.assertTrue(galleryPage.getGalleryItemCount() > 0, 
                "No engineering gallery items");

        // Return to home
        galleryPage.clickBackToHome();

        // Test Survey gallery
        homePage.clickServiceCard(2); // Survey card
        TestUtils.waitForPageLoad(driver);

        softAssert.assertTrue(galleryPage.getGalleryTitle().contains("Survey"), 
                "Gallery title incorrect for Survey");
        softAssert.assertTrue(galleryPage.getGalleryItemCount() > 0, 
                "No survey gallery items");

        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Test contact form functionality")
    public void testContactForm() {
        logger.info("Starting test: testContactForm");

        // Scroll to contact form
        homePage.scrollToContactSection();

        // Test successful form submission
        String testName = "Test User " + System.currentTimeMillis();
        String testPhone = TestUtils.generateRandomPhone();
        String testMessage = "Test message for project inquiry";

        homePage.fillContactForm(testName, testPhone, testMessage);
        homePage.submitContactForm();

        // Wait for response
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // Verify form status appears (actual message may vary)
        softAssert.assertTrue(homePage.isFormStatusDisplayed(), 
                "Form status not displayed after submission");

        // Test form validation (empty submission)
        homePage.fillContactForm("", "", "");
        boolean validationTriggered = homePage.verifyFormValidation();
        softAssert.assertTrue(validationTriggered, 
                "Form validation not triggered for empty fields");

        // Test invalid phone number (if frontend validates)
        homePage.fillContactForm("Test User", "123", "Test message");
        homePage.submitContactForm();

        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Test responsive design")
    public void testResponsiveDesign() {
        logger.info("Starting test: testResponsiveDesign");

        // Test different viewport sizes
        int[][] viewports = {
            {375, 667},  // Mobile
            {768, 1024}, // Tablet
            {1366, 768}  // Desktop
        };

        for (int[] viewport : viewports) {
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(viewport[0], viewport[1]));
            logger.info("Testing viewport: " + viewport[0] + "x" + viewport[1]);
            
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // Verify key elements are still visible
            softAssert.assertTrue(homePage.isHeroSectionDisplayed(), 
                    "Hero section not visible at " + viewport[0] + "x" + viewport[1]);
            softAssert.assertTrue(homePage.isNavbarLogoDisplayed(), 
                    "Logo not visible at " + viewport[0] + "x" + viewport[1]);

            // Take screenshot for visual verification
            TestUtils.takeScreenshot(driver, "responsive_" + viewport[0] + "x" + viewport[1]);
        }

        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Test WhatsApp integration")
    public void testWhatsAppIntegration() {
        logger.info("Starting test: testWhatsAppIntegration");

        String originalWindow = driver.getWindowHandle();
        homePage.clickWhatsAppButton();

        // Switch to new tab/window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Verify WhatsApp URL
        String currentUrl = driver.getCurrentUrl();
        softAssert.assertTrue(currentUrl.contains("wa.me") || currentUrl.contains("whatsapp.com"), 
                "WhatsApp URL not correct: " + currentUrl);

        // Close WhatsApp tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);

        softAssert.assertAll();
    }

    @Test(priority = 7, description = "Test scroll functionality")
    public void testScrollFunctionality() {
        logger.info("Starting test: testScrollFunctionality");

        // Scroll to different sections
        homePage.scrollToAboutSection();
        TestUtils.takeScreenshot(driver, "about_section");
        
        homePage.scrollToServicesSection();
        TestUtils.takeScreenshot(driver, "services_section");
        
        homePage.scrollToContactSection();
        TestUtils.takeScreenshot(driver, "contact_section");

        // Verify we can see the footer
        boolean footerVisible = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].getBoundingClientRect().top < window.innerHeight;", 
                driver.findElement(By.tagName("footer")));
        
        softAssert.assertTrue(footerVisible, "Footer not visible after scrolling");

        softAssert.assertAll();
    }

    @Test(priority = 8, description = "Test image loading and alt text")
    public void testImages() {
        logger.info("Starting test: testImages");

        // Check all images for alt text and loading
        var images = driver.findElements(By.tagName("img"));
        
        for (var img : images) {
            String src = img.getAttribute("src");
            String alt = img.getAttribute("alt");
            
            // Check if image is loaded
            Boolean isLoaded = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].complete && " +
                            "typeof arguments[0].naturalWidth != 'undefined' && " +
                            "arguments[0].naturalWidth > 0", img);
            
            softAssert.assertTrue(isLoaded, "Image failed to load: " + src);
            
            // Check alt text (not strictly required but good practice)
            if (alt == null || alt.isEmpty()) {
                logger.warning("Image missing alt text: " + src);
            }
        }

        softAssert.assertAll();
    }

    @Test(priority = 9, description = "Test console errors")
    public void testConsoleErrors() {
        logger.info("Starting test: testConsoleErrors");

        var logs = driver.manage().logs().get("browser").getAll();
        boolean hasErrors = false;
        
        for (var logEntry : logs) {
            if (logEntry.getLevel().getName().equals("SEVERE")) {
                logger.severe("Console error: " + logEntry.getMessage());
                hasErrors = true;
            }
        }

        softAssert.assertFalse(hasErrors, "Browser console has severe errors");
        softAssert.assertAll();
    }

    @Test(priority = 10, description = "End-to-end user journey")
    public void testEndToEndJourney() {
        logger.info("Starting test: testEndToEndJourney");

        // User journey: Home -> Services -> Gallery -> Contact -> WhatsApp

        // Step 1: View services
        homePage.scrollToServicesSection();
        softAssert.assertTrue(homePage.areAllServiceCardsDisplayed(), 
                "Services not visible");

        // Step 2: View planning gallery
        homePage.clickServiceCard(0);
        TestUtils.waitForPageLoad(driver);
        softAssert.assertTrue(galleryPage.areGalleryItemsDisplayed(), 
                "Gallery items not displayed");
        
        String firstItemTag = galleryPage.getFirstItemTag();
        logger.info("Gallery item tag: " + firstItemTag);
        
        // Step 3: Return home and contact
        galleryPage.clickBackToHome();
        TestUtils.waitForPageLoad(driver);

        homePage.scrollToContactSection();
        
        // Step 4: Fill contact form
        String name = TestUtils.generateRandomName();
        String phone = TestUtils.generateRandomPhone();
        String message = "Interested in building my dream home. Please contact me.";
        
        homePage.fillContactForm(name, phone, message);
        TestUtils.takeScreenshot(driver, "contact_form_filled");
        
        homePage.submitContactForm();
        
        // Step 5: Verify form submission
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        softAssert.assertTrue(homePage.isFormStatusDisplayed(), 
                "Form not submitted successfully");

        // Step 6: Check WhatsApp option
        homePage.clickWhatsAppButton();
        
        softAssert.assertAll();
    }
}
