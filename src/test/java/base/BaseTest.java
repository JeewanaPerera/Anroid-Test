package base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {
    protected AndroidDriver driver;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private String appPackage;
    private String deviceId = System.getProperty("deviceId","10.0.0.116:5555");
    @Parameters({"appPackage", "appActivity", "gridUrl"})
    @BeforeTest
    public void setUp(String appPackage, String appActivity, String gridUrl) {
        LOGGER.log(Level.INFO,"Setup base, deviceId " + deviceId);
        if (driver == null) {
            LOGGER.log(Level.INFO,"Drivers availabFle");
            try {
                DesiredCapabilities caps = getDesiredCapabilities(appPackage, appActivity);
                driver = new AndroidDriver(new URL(gridUrl), caps);

                // Setting an implicit wait to handle slower device response
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                LOGGER.log(Level.INFO, "Driver initialized successfully.");

            } catch (MalformedURLException e) {
                LOGGER.log(Level.SEVERE, "Malformed URL for the Appium server: " + gridUrl, e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize the driver.", e);
            }
        }
    }

    private DesiredCapabilities getDesiredCapabilities(String appPackage, String appActivity) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:udid", deviceId);//ce02182229bb203404
        caps.setCapability("appium:deviceName", "SM-G950F");
        caps.setCapability("noReset", false);
        caps.setCapability("appium:appPackage", appPackage);
        caps.setCapability("appium:appActivity", appActivity);
        caps.setCapability("autoGrantPermissions", true);
        return caps;
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            try {
                // Close the application explicitly by its package name
                driver.terminateApp(appPackage);
                LOGGER.log(Level.INFO, "Application terminated successfully.");

                // Quit the driver session
                driver.quit();
                LOGGER.log(Level.INFO, "Driver closed successfully.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error while terminating the application or closing the driver.", e);
            }
        }
    }

    private static String getApiKeyFromFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            return json.get("apiKey").getAsString();
        } catch (Exception e) {
            System.err.println("Error reading API key from file: " + e.getMessage());
            return null;
        }
    }
}