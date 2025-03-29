package TikTok_Tests;

import base.BaseTest2;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TikTokHelper;
import utils.TikTokLocators;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TikTokProfileTest extends BaseTest2 {
    private static final Logger LOGGER = Logger.getLogger(TikTokProfileTest.class.getName());
    
    @Test
    public void readProfileId() throws InterruptedException {
        LOGGER.log(Level.INFO, "Starting TikTok profile ID test");
        
        // Initialize TikTokHelper with the driver
        TikTokHelper tikTokHelper = new TikTokHelper((AndroidDriver) driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            // Allow some time for the app to fully load
            Thread.sleep(3000);
            LOGGER.log(Level.INFO, "TikTok app loaded");
            
            // Navigate to the profile page
            tikTokHelper.goToProfile();
            LOGGER.log(Level.INFO, "Navigated to profile page");
            
            // Wait for profile page to load
            Thread.sleep(2000);
            
            // Look for profile username (which is the TikTok ID)
            WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(TikTokLocators.ACCOUNT_ID));
            String username = usernameElement.getText();
            System.out.println("TikTok ID: " + username);



            // Take a screenshot of the profile page
            LOGGER.log(Level.INFO, "Profile information extracted successfully");
            Thread.sleep(1000);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during profile ID extraction", e);
            throw e;
        }
    }
}