package base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest2 {
    protected AndroidDriver driver;
    private static final Logger LOGGER = Logger.getLogger(BaseTest2.class.getName());
    private String appPackage;
    private String deviceId = System.getProperty("deviceId","10.0.0.116:5555");

    @Parameters({"appPackage", "appActivity", "gridUrl"})
    @BeforeClass(alwaysRun = true)
    public void setUp(String appPackage, String appActivity, String gridUrl) {
        this.appPackage = appPackage;


        if (driver == null) {
            LOGGER.log(Level.INFO, "Initializing driver");
            try {
                // Check if app is installed using adb before creating driver
                String apkPath = getApkPath(appPackage);
                LOGGER.log(Level.INFO, "APK path: " + apkPath + " Exists: " + new File(apkPath).exists());

                // Create capabilities
                DesiredCapabilities caps = getDesiredCapabilities(appPackage, appActivity);

                // If APK exists, add it to capabilities to ensure installation
                if (new File(apkPath).exists()) {
                    LOGGER.log(Level.INFO, "Adding APK to capabilities for installation");
                    caps.setCapability("app", apkPath);
                } else {
                    LOGGER.log(Level.SEVERE, "APK file not found at: " + apkPath);
                    //Only for Tiktok, Youtbe is preinstalled
                    if(appPackage.equals("com.zhiliaoapp.musically")){throw new FileNotFoundException("APK file not found");}
                }

                // Initialize driver with capabilities (will install app if specified in capabilities)
                driver = new AndroidDriver(new URL(gridUrl), caps);

                // Set implicit wait after successful initialization
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                LOGGER.log(Level.INFO, "Driver initialized successfully.");

            } catch (MalformedURLException e) {
                LOGGER.log(Level.SEVERE, "Malformed URL for the Appium server: " + gridUrl, e);
                throw new RuntimeException(e);
            }/* catch (FileNotFoundException e) {
                LOGGER.log(Level.SEVERE, "APK file not found", e);
                throw new RuntimeException(e);
            }*/ catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize the driver.", e);
                throw new RuntimeException(e);
            }
        }

        // Attempt to kill the app before starting a new session
        LOGGER.log(Level.INFO, "Start setups " + appPackage);
        try {
            killApp(deviceId,appPackage);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to kill app: " + appPackage, e);
        }

    }
    private String getApkPath(String appPackage) {
        // Get project root directory
        String projectRoot = System.getProperty("user.dir");
        String apkName;

        switch (appPackage) {
            case "com.zhiliaoapp.musically":
                apkName = "tiktok.apk";
                break;
            case "com.google.android.youtube":
                apkName = "youtube.apk";
                break;
            default:
                LOGGER.log(Level.SEVERE, "Unsupported app package: " + appPackage);
                return null;
        }

        return new File(projectRoot).getParentFile().getPath() + "\\apks\\" + apkName;
    }
    private DesiredCapabilities getDesiredCapabilities(String appPackage, String appActivity) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:adbInstallTimeout", "180000");
        caps.setCapability("androidInstallTimeout", "180000");
        caps.setCapability("adbExecTimeout", "180000");
        caps.setCapability("appium:udid", deviceId);
        caps.setCapability("appium:deviceName", "SM-G950F");  // Replace with your device's name
        caps.setCapability("noReset", true);
        caps.setCapability("appium:appPackage", appPackage);
        caps.setCapability("appium:appActivity", appActivity);
//        caps.setCapability("autoGrantPermissions", true);

        return caps;
    }

    public static void killApp(String deviceId, String packageName) throws IOException {
        String command = "adb  -s "+  deviceId+"shell am force-stop " + packageName;
        Runtime.getRuntime().exec(command);
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
}
