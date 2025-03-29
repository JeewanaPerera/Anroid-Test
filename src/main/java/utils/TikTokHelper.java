package utils;
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
import java.util.logging.Logger;

public class TikTokHelper {

    private final AndroidDriver driver; // Changed to AndroidDriver
    private final WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(TikTokHelper.class.getName());

    public TikTokHelper(AndroidDriver driver) { // Changed to AndroidDriver
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

    public void clickOnMainPageSearchButton() throws InterruptedException {
        Thread.sleep(1000);
        var mainSearchButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.MAIN_SEARCH_BUTTON));
        mainSearchButton.click();
        Thread.sleep(500);
    }

    public void inputSearchValue(String searchValue) throws InterruptedException {
        var searchInput = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.SEARCH_INPUT));
        searchInput.click();
        searchInput.sendKeys(searchValue);
        Thread.sleep(500);
    }

    // Click on search button
    public void searchVideo() throws InterruptedException {
        var searchButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.SEARCH_BUTTON));
        searchButton.click();
        Thread.sleep(1000);
    }

    // Select videos tab
    public void selectVideosTab() throws InterruptedException {
        Thread.sleep(1000);
        var videosTabButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.VIDEOS_TAB_BUTTON));
        videosTabButton.click();
        Thread.sleep(1000);
    }

    // Select users tab
    public void selectUsersTab() throws InterruptedException {
        Thread.sleep(1000);
        var usersTabButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.USERS_TAB_BUTTON));
        usersTabButton.click();
        Thread.sleep(1000);
    }

    // Select first user
    public void selectFirstUser() {
        var firstUser = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.FIRST_USER));
        firstUser.click();
    }

    // Open latest video
    public void openLastVideo() throws InterruptedException {
        var lastVideo = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.LAST_VIDEO));
        lastVideo.click();
        Thread.sleep(1000);
    }

    // Print captions
    public void printCaptions() {
        var caption = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.CAPTION));
        System.out.println(caption.getText());
    }

    // Like/Unlike
    public void likeUnlike() {
        var likeButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.LIKE_BUTTON));
        likeButton.click();
    }

    // Like if not liked
    public void likeIfNotLiked() {
        var likeButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.LIKE_BUTTON));
        if (likeButton.getAttribute("selected").equalsIgnoreCase("false")) {
            likeButton.click();
        }
    }

    // Click on comment button
    public void clickOnComment() throws InterruptedException {
        var commentButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.COMMENT_BUTTON));
        commentButton.click();
        Thread.sleep(500);
    }

    // Activate comment field
    public void activateCommentField() {
        var commentField = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.COMMENT_FIELD));
        commentField.click();
    }

// Input comment
    public void inputComment(String comment) {
        var commentField = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.COMMENT_FIELD));
        commentField.click();
        commentField.sendKeys(comment);
    }


    // Post comment
    public void postComment() {
        var commentField = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.COMMENT_FIELD));
        commentField.click();
    }

    // Explore
    public void explore() throws InterruptedException {
        var exploreButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.EXPLORE_BUTTON));
        exploreButton.click();
        Thread.sleep(500);
    }

    // Click on search button by coordinates
    public void clickOnSearchCoordinates() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 0);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), TikTokLocators.SEARCH_BUTTON_X, TikTokLocators.SEARCH_BUTTON_Y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    // Open random video
    public void openRandomVideo() throws InterruptedException {
        // Locate all elements matching the XPath
        List<WebElement> videos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(TikTokLocators.VIDEO_COVERS));

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

    // Click on share
    public void clickOnShare() {
        var shareButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.SHARE_BUTTON));
        shareButton.click();
    }

    // Repost
    public void repost() {
        LOGGER.info("Reposting");
        var repostButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.REPOST_BUTTON));
        repostButton.click();
    }

    // Swipe and repost
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
                    System.out.println("Unable to repost");
                }
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Swipe and follow users
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
                    System.out.println("Unable to follow the user.");
                }
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Click on video title
    public void clickOnVideoTitle() {
        var titleButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.VIDEO_TITLE));
        titleButton.click();
    }

    // Click on follow button
    public void clickOnUserFollowButton() {
        try {
            var followButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.FOLLOW_BUTTON));
            followButton.click();
        } catch (TimeoutException e) {
            System.out.println("Follow button not found, skipping this step.");
        }
    }

    // Swipe from left to right
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

    // Go to profile
    public void goToProfile() throws InterruptedException {
        var profileButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.PROFILE_BUTTON));
        profileButton.click();
        Thread.sleep(500);
    }

    // Go to followers tab
    public void goToFollower() {
        var followersTab = wait.until(ExpectedConditions.presenceOfElementLocated(TikTokLocators.FOLLOWERS_TAB));
        followersTab.click();
    }

    // Swipe
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

    // Unfollow random
    public void unfollowRandom(int n) {
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            List<WebElement> followingElements = driver.findElements(TikTokLocators.FOLLOWING_ELEMENTS);
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


    // Click on main upload button
    public void clickOnMainUpload() throws InterruptedException {
        Thread.sleep(500);
        var mainUploadButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.MAIN_UPLOAD_BUTTON));
        mainUploadButton.click();
        Thread.sleep(1000);
    }

    // Open gallery
    public void openGallery() throws InterruptedException {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 0);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), TikTokLocators.OPEN_GALLERY_X, TikTokLocators.OPEN_GALLERY_Y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
        Thread.sleep(1000);
    }

    // Click on latest video
    public void clickOnLatestVideo() throws InterruptedException {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 0);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), TikTokLocators.CLICK_LATEST_VIDEO_X, TikTokLocators.CLICK_LATEST_VIDEO_Y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
        Thread.sleep(1000);
    }

    // Click on next button
    public void clickOnNextButton() {
        var nextButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.NEXT_BUTTON));
        nextButton.click();
    }

    // Click on second next button
    public void clickOnSecondNextButton() {
        var secondNextButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.SECOND_NEXT_BUTTON));
        secondNextButton.click();
    }

    // Post
    public void post() {
        var postButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.POST_BUTTON));
        postButton.click();
    }

    // Edit profile
    public void editProfile() {
        var editProfileButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.EDIT_PROFILE_BUTTON));
        editProfileButton.click();
    }

    // Click on name field
    public void clickOnNameField() {
        var nameField = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.NAME_FIELD));
        nameField.click();
    }

    // Click on username field
    public void clickOnUserNameField() {
        var userNameField = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.USERNAME_FIELD));
        userNameField.click();
    }

    // Input username
    public void inPutUsername(String username) throws InterruptedException {
        var input = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.INPUT_FIELD));
        Actions actions = new Actions(driver);
        actions.doubleClick(input).perform();
        input.sendKeys(username);
        Thread.sleep(500);
    }

    // Save
    public void save() {
        var saveButton = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.SAVE_BUTTON));
        saveButton.click();
    }

    // Click on bio
    public void clickOnBio() {
        var bioField = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.BIO_FIELD));
        bioField.click();
    }

    // Activate bio field
    public void activateBioField(String bio) throws InterruptedException {
        var input = wait.until(ExpectedConditions.elementToBeClickable(TikTokLocators.INPUT_FIELD));
        input.click();
        input.sendKeys(bio);
        Thread.sleep(500);
    }
}
