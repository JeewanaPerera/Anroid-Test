package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TikTokHelper_old {
    private final AndroidDriver driver; // Changed to AndroidDriver
    private final WebDriverWait wait;

    public TikTokHelper_old(AndroidDriver driver) { // Changed to AndroidDriver
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static int getRandomNumberBetween(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

public void swipeWatchLike(int swipeCount) throws InterruptedException {
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;
    int startX = width / 2;
    int startY = (int) (height * 0.7); // Start higher on the screen (70% from the top)
    int endY = (int) (height * 0.4); // Adjusted end position for a smoother swipe

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    for (int i = 0; i < swipeCount; i++) {
        Sequence swipe = new Sequence(finger, 1);

        // Start swipe from the adjusted higher position
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
        Thread.sleep(getRandomNumberBetween(1000, 4000));

        if (i % 2 == 0) {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                likeIfNotLiked();
            } catch (TimeoutException e) {
                System.out.println("Like button not visible, skipping click.");
            }
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


    public void swipeRightToLeft(int swipeCount) throws InterruptedException {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int startX = (int) (width * 0.9); // Start near the right edge
        int endX = (int) (width * 0.1);  // End near the left edge
        int startY = height / 2;        // Middle of the screen vertically

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        for (int i = 0; i < swipeCount; i++) {
            Sequence swipe = new Sequence(finger, 1);

            // Move finger to start position
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            // Press down at the starting point
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            // Move finger from right to left
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, startY));
            // Release the finger
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the swipe gesture
            driver.perform(List.of(swipe));

            // Pause between swipes
            Thread.sleep(1000);
        }
    }



// CLICK ON SEARCH BUTTON
public void clickOnMainPageSearchButton() throws InterruptedException {
    Thread.sleep(1000);
    var mainsearchButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.ImageView)[8]")));
    mainsearchButton.click();
    Thread.sleep(500);
}
//INPUT SEARCH VALUE
public void inputSearchValue(String searchValue) throws InterruptedException {
    var searchInput = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText")));
    searchInput.click();
    searchInput.sendKeys(searchValue);
    Thread.sleep(500);
}
//CLICK on search button
public void searchVideo() throws InterruptedException {
    var searchButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.Button")));
    searchButton.click();
    Thread.sleep(1000);
}
//select videos tab
public void selectVideosTab() throws InterruptedException {
    Thread.sleep(1000);
    var videosTabButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().text(\"Videos\")")));
//    var usersTabButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.TextView[@text='Users']")));
    videosTabButton.click();
    Thread.sleep(1000);

}

//select user tab
public void selectUsersTab() throws InterruptedException {
    Thread.sleep(1000);
    var usersTabButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().text(\"Users\").instance(0)")));
//    var usersTabButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.TextView[@text='Users']")));
    usersTabButton.click();
    Thread.sleep(1000);

}
//select first user
public void selectFirstUser() {
    var fuser = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(4)")));
    fuser.click();

}
//open latest video
public void openLastVideo() throws InterruptedException {
    var lvideo = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.ImageView[@resource-id='com.zhiliaoapp.musically:id/cover'])[1]")));
    lvideo.click();
    Thread.sleep(1000);
}
//print captions
public void printCaptions() {
    var caption = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("com.bytedance.tux.input.TuxTextLayoutView")));
    System.out.println(caption.getText());
}
//like Unlike
public void likeUnlike() {
    var caption = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Like")));
    caption.click();
}

//likeIfUnliked
public void likeIfNotLiked() {
    var caption = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Like")));
    if (caption.getAttribute("selected").equalsIgnoreCase("false")) {
        caption.click();
    }
}
//click on comment button
public void clickOnComment() throws InterruptedException {
    var commentButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[contains(@content-desc, 'Read or add comments')]")));
    commentButton.click();
    Thread.sleep(500);
}
//activate comment field
public void activateCommentField() {
    var commentButtonactivate = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText")));
    commentButtonactivate.click();
}
//input comment
public void inputComment() {
    var commentButtonActivate = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText")));
    commentButtonActivate.click();
    commentButtonActivate.sendKeys("wow");
}
//post comment
public void postComment() {
    var comment = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText")));
    comment.click();
}


public void explore() throws InterruptedException {
    var exploreButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().text(\"Explore\")")));
    exploreButton.click();
    Thread.sleep(500);

}

//click on search button by cordinates
public void clickOnSearchCordinates(){
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence tap = new Sequence(finger, 0);
    tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
            PointerInput.Origin.viewport(), 671, 97)); // Coordinates
    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

    driver.perform(Arrays.asList(tap));
}

public void openRandomVideo() throws InterruptedException {
    // Locate all elements matching the XPath
    List<WebElement> videos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.zhiliaoapp.musically:id/cover']")));

    // Check if the list is not empty
    if (!videos.isEmpty()) {
        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(videos.size());

        // Click on the randomly selected video
        videos.get(randomIndex).click();

        // Optional: Add a short wait
        Thread.sleep(1000);
    } else {
        System.out.println("No videos found on the page.");
    }
}



public void clickOnShare() {
    var caption = wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.xpath("//*[contains(@content-desc, 'Share video')]")
    ));
    caption.click();
}

public void repost() {
    var repostButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().text(\"Repost\")")));
    repostButton.click();
}

public void swipeRepost(int swipeCount) throws InterruptedException {
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;
    int startX = width / 2;
    int startY = (int) (height * 0.7); // Start higher on the screen (70% from the top)
    int endY = (int) (height * 0.4); // Adjusted end position for a smoother swipe
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    for (int i = 0; i < swipeCount; i++) {
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
        Thread.sleep(getRandomNumberBetween(1000, 4000));

        if (i % 2 == 0) {
            try {
                clickOnShare();
                repost();
                Thread.sleep(300);
            } catch (TimeoutException e) {
                System.out.println("unable to repost");
            }
        }
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


    public void swipeFollow(int swipeCount) throws InterruptedException {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int startX = width / 2;
        int startY = (int) (height * 0.7); // Start higher on the screen (70% from the top)
        int endY = (int) (height * 0.4); // Adjusted end position for a smoother swipe
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        for (int i = 0; i < swipeCount; i++) {
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(List.of(swipe));
            Thread.sleep(getRandomNumberBetween(1000, 4000));

            if (i % 2 == 0) {
                try {
                    clickOnVideoTitle();
                    Thread.sleep(500);
                    clickOnUserFollowButton();
                    Thread.sleep(2000);
                    swipeLeftToRight(1);
                    Thread.sleep(300);
                } catch (TimeoutException e) {
                    System.out.println("unable to repost");
                }
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickOnVideoTitle() {
        var titleButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.zhiliaoapp.musically:id/title\")")));
        titleButton.click();
    }
    public void clickOnUserFollowButton() {
        try {
            var followButton = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"Follow\")")
            ));
            followButton.click();
        } catch (TimeoutException e) {
            System.out.println("Follow button not found, skipping this step.");
        }
    }

    public void swipeLeftToRight(int swipeCount) throws InterruptedException {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int startX = (int) (width * 0.1);
        int endX = (int) (width * 0.9);
        int startY = height / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        for (int i = 0; i < swipeCount; i++) {
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, startY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(swipe));
            Thread.sleep(1000);
        }
    }

    public void goToProfile() throws InterruptedException {
        var profileButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@content-desc=\"Profile\"]/android.widget.ImageView")));
        profileButton.click();
        Thread.sleep(500);
    }

    public void goToFollower() {
        var followersTab = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Following\")")
        ));
        followersTab.click();
    }

    public void swipe(int swipeCount) throws InterruptedException {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int startX = width / 2;
        int startY = (int) (height * 0.7);
        int endY = (int) (height * 0.4);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        for (int i = 0; i < swipeCount; i++) {
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(List.of(swipe));
            Thread.sleep(getRandomNumberBetween(700, 1200));
        }
    }


    public void unfollowRandom(int n) {
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            List<WebElement> followingElements = driver.findElements(AppiumBy.androidUIAutomator("text(\"Following\")"));
            System.out.println("Size of 'Following' list: " + followingElements.size());

            if (!followingElements.isEmpty()) {
                int randomIndex = random.nextInt(followingElements.size());
                WebElement elementToClick = followingElements.get(randomIndex);

                try {
                    wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
                    elementToClick.click();
                } catch (TimeoutException e) {
                    System.out.println("Element not clickable. Skipping...");
                }
            } else {
                System.out.println("No 'Following' elements found.");
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickOnMainUpload() throws InterruptedException {
        Thread.sleep(500);
        var mainUploadButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[@content-desc='Create']/android.widget.ImageView")));
        mainUploadButton.click();
        Thread.sleep(1000);
    }

    public void openGalery() throws InterruptedException {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 0);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), 590, 1255)); // Coordinates
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
        Thread.sleep(1000);
    }


    public void clickOnLatesVideo() throws InterruptedException {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 0);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), 120, 345)); // Coordinates
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
        Thread.sleep(1000);
    }

    public void clickOnNextButton() {
        var nextButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.Button[@text='Next'])[2]")));
        nextButton.click();
    }

    public void clickOnSecondNextButton() {
        var secondNextButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.TextView[@text='Next'])")));
        secondNextButton.click();
    }

    public void post() {
        var postButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Post")));
        postButton.click();
    }

    public void editProfile() {
        var editProfileButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Edit profile")));
        editProfileButton.click();
    }

    public void clickOnNameField() {
        var nameField = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//*[contains(@content-desc, 'Name')]")
        ));
        nameField.click();
    }

    public void clickOnUserNameField() {
        var userNameField = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//*[contains(@content-desc, 'Username')]")
        ));
        userNameField.click();
    }

    public void inPutUsername(String username) throws InterruptedException {
        var input = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText")));
        Actions actions = new Actions(driver);
        actions.doubleClick(input).perform();
        input.sendKeys(username);
        Thread.sleep(500);
    }

    public void save() {
        var saveButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Save")));
        saveButton.click();
    }

    public void clickOnBio() {
        var bioField = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//*[contains(@content-desc, 'Bio')]")
        ));
        bioField.click();
    }

    public void activateBioField(String bio) throws InterruptedException {
        var input = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText")));
        input.click();
        input.sendKeys(bio);
        Thread.sleep(500);
    }

}