package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.logging.Logger;

public class YouTubeHelper {
    private final AndroidDriver driver; // Changed to AndroidDriver
    private final WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(YouTubeHelper.class.getName());

    public YouTubeHelper(AndroidDriver driver) { // Changed to AndroidDriver
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));//Increased to wait for ads
    }

    // Subscribe to a specific channel
    public void subscribeToChannel(String channelName) {
        WebElement subscribeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(String.format(YouTubeLocators.SUBSCRIBE_BUTTON_DESCRIPTION, channelName))));
        subscribeButton.click();
        System.out.println("Subscribed");
    }

    // Generic subscribe method
    public void subscribe() {
        try {
            List<WebElement> subscribeButtons = driver.findElements(YouTubeLocators.GENERIC_SUBSCRIBE_BUTTON);

            if (!subscribeButtons.isEmpty()) {
                WebElement subscribeButton = subscribeButtons.get(0);
                subscribeButton.click();
                System.out.println("Subscribed successfully.");
            } else {
                System.out.println("Already subscribed.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to subscribe: " + e.getMessage());
        }
    }

    // Go to author's channel
    public void goToAuthor() {
        var channel = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.GO_TO_AUTHOR_CHANNEL));
        channel.click();
    }

    // Go to author's latest videos tab
    public void goToAuthorLatestVideos() {
        var videos = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.AUTHOR_LATEST_VIDEOS_TAB));
        videos.click();
        try {
            var latest = wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.LATEST_TAB));
            if (latest.isDisplayed()) {
                latest.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Latest Video tab is not displayed");
        }
    }

    // Reply to a comment
    public void replyToComment(String text) {
        try {
            var replyButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.REPLY_BUTTON));
            if (replyButton.isDisplayed()) {
                replyButton.click();
            } else {
                var replies = driver.findElement(YouTubeLocators.REPLIES_SECTION);
                replies.click();
            }
            var replyField = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(YouTubeLocators.ADD_REPLY_FIELD)));
            replyField.click();
            replyField.sendKeys(text);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    // Search for an author
    public void searchAuthor(String authorName) {
        WebElement searchButton = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(YouTubeLocators.SEARCH_BUTTON)));
        searchButton.click();

        WebElement searchInput = driver.findElement(YouTubeLocators.SEARCH_INPUT);
        searchInput.sendKeys(authorName);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    // Search for a video
    public void searchVideo(String query) {
        WebElement searchButton = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(YouTubeLocators.SEARCH_BUTTON)));
        searchButton.click();

        WebElement searchInput = driver.findElement(YouTubeLocators.SEARCH_INPUT);
        searchInput.sendKeys(query);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    // Click on the first search result
    public void clickFirstSearchResult() {
        var firstResult = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.FIRST_SEARCH_RESULT));
        firstResult.click();
    }

    // Select the first video play button
    public void selectFirstVideoPlayButton() {
        WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.FIRST_VIDEO_PLAY_BUTTON));
        playButton.click();
    }

    // Get all search results and return the nth video
    public WebElement getAllSearchResults(Integer nthVideo) {
        var elements = driver.findElements(YouTubeLocators.SEARCH_RESULT_LIST);

        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
        return elements.get(nthVideo);
    }

    // Like a video
    public void likeVideo() {
        var likeButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.LIKE_BUTTON));
        likeButton.click();
    }

    // Dislike a video
    public void dislikeVideo() {
        var dislikeButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.DISLIKE_BUTTON));
        dislikeButton.click();
    }

    // Navigate to comments section
    public void navigateToCommentsSection() {
        var comments = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.COMMENTS_SECTION));
        comments.click();
    }

    // Select a specific comments tab
    public void selectCommentsTab(String tabName) {
        navigateToCommentsSection();
        var tab = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(String.format(YouTubeLocators.COMMENTS_TAB_ACCESSIBILITY, tabName))));
        tab.click();
    }

    // Go to "Newest" comments tab
    public void goToNewestCommentsTab() {
        var newestTab = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.NEWEST_TAB));
        newestTab.click();
    }

    // Go to "Top" comments tab
    public void goToTopCommentsTab() {
        var topTab = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.TOP_TAB));
        topTab.click();
    }

    // Check if an element is displayed
    private boolean isElementDisplayed(By locator) {
        try {
            var element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Create a post
    public void createPost(String postDescription) throws InterruptedException {
        driver.findElement(YouTubeLocators.SHARE_BUTTON).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.DESIGN_BOTTOM_SHEET));
        driver.findElement(YouTubeLocators.CREATE_POST).click();
        Thread.sleep(2000);
        var contentEditBox = driver.findElement(YouTubeLocators.CONTENT_EDIT_BOX);
        contentEditBox.sendKeys(postDescription);
        var postButton = driver.findElement(YouTubeLocators.POST_BUTTON);
        if (postButton.isEnabled()) {
            postButton.click();
        }
    }

    // Play a random suggested video
    public void playRandomSuggestedVideo() {
        var firstSuggestedVideo = driver.findElement(YouTubeLocators.WATCH_LIST).findElement(YouTubeLocators.SUGGESTED_VIDEO_PLAY_BUTTON);
        firstSuggestedVideo.click();
    }

    // Save a video to "Watch Later"
    public void saveToWatchLater() {
        try {
            WebElement saveToPlaylistButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.SAVE_TO_PLAYLIST_BUTTON));

            if (saveToPlaylistButton.isDisplayed()) {
                saveToPlaylistButton.click();
                System.out.println("Clicked 'Save to playlist' button.");

                try {
                    WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.MESSAGE));
                    System.out.println("Message displayed: " + message.getText());
                } catch (TimeoutException e) {
                    WebElement doneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.DONE_BUTTON));
                    if (doneButton.isDisplayed()) {
                        doneButton.click();
                        System.out.println("Video already saved, clicked 'Done' button.");
                    } else {
                        System.out.println("Done button not displayed.");
                    }
                }
            } else {
                System.out.println("'Save to playlist' button not displayed.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Add a comment
    public void addComment(String comment) throws InterruptedException {
        navigateToCommentsSection();
        Thread.sleep(2000);
        var addCommentField = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.ADD_COMMENT_FIELD));
        addCommentField.click();
        addCommentField.sendKeys(comment);
        var sendCommentButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.SEND_COMMENT_BUTTON));
        sendCommentButton.click();
    }

    // Get video name from a JSON file
    public String getVideoNameFromJson(String filePath) {
        String videoName = null;
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            videoName = jsonObject.getString("videoName");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoName;
    }

    // Save unique usernames to a JSON file
    public void saveUniqueUsernamesToJson(String filePath) {
        Set<String> uniqueDescriptions = new HashSet<>();
        JSONArray userArray = new JSONArray();
        WebElement engagementPanel = driver.findElement(YouTubeLocators.ENGAGEMENT_PANEL);

        int startX = engagementPanel.getLocation().getX() + engagementPanel.getSize().getWidth() / 2;
        int startY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.8);
        int endY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.2);

        boolean hasMoreToScroll = true;

        while (hasMoreToScroll) {
            List<WebElement> elements = engagementPanel.findElements(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"@\")"));
            for (WebElement el : elements) {
                String description = el.getAttribute("contentDescription");
                if (uniqueDescriptions.add(description)) {
                    userArray.put(description);
                    System.out.println("Unique Username Found: " + description);
                }
            }

            new TouchAction<>(driver).press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(startX, endY)).release().perform();

            hasMoreToScroll = !elements.isEmpty();
        }

        try (FileWriter file = new FileWriter(filePath)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("usernames", userArray);
            file.write(jsonObject.toString(4));
            System.out.println("Usernames saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the total time of the video
    public void getTotalTimeOfTheVideo() {
        driver.findElement(YouTubeLocators.VIDEO_PLAYER).click();
        driver.findElement(YouTubeLocators.PLAY_PAUSE_BUTTON).click();
        WebElement timeElement = driver.findElement(YouTubeLocators.TOTAL_TIME_TEXT);
        String[] parts = timeElement.getText().split("/ ");
        String result = parts.length > 1 ? parts[1] : "";
        System.out.println(result);
    }

    public void moveSeekBarAtTheEndOfTheVideo() {
        // Click the video player to ensure the seek bar is visible
        driver.findElement(YouTubeLocators.VIDEO_PLAYER).click();

        // Locate the seek bar and calculate its dimensions
        WebElement seekBar = driver.findElement(YouTubeLocators.SEEK_BAR);
        Rectangle bounds = seekBar.getRect();
        int seekBarWidth = bounds.getWidth();
        int seekBarStartX = bounds.getX();
        int seekBarStartY = bounds.getY() + (bounds.getHeight() / 2); // Middle of the seek bar

        int targetX = seekBarStartX + seekBarWidth; // Move to the end of the seek bar

        // Perform the drag action using W3C actions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence drag = new Sequence(finger, 0);
        drag.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), seekBarStartX, seekBarStartY));
        drag.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        drag.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), targetX, seekBarStartY));
        drag.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(drag));
    }


    public void showAndLogTranscripts() throws InterruptedException {
        // Click the first item in the watch list
        var watchList = driver.findElement(YouTubeLocators.WATCH_LIST);
        watchList.findElement(YouTubeLocators.WATCH_LIST_ITEM).click();

        // Scroll the content area to ensure the "Show transcript" button is visible
        WebElement content = driver.findElement(YouTubeLocators.CONTENT_AREA);
        int startX = content.getLocation().getX() + content.getSize().getWidth() / 2;
        int startY = content.getLocation().getY() + (int) (content.getSize().getHeight() * 0.8);
        int endY = content.getLocation().getY() + (int) (content.getSize().getHeight() * 0.1);

        for (int i = 0; i < 2; i++) {
            new TouchAction<>(driver).press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(startX, endY)).release().perform();
        }

        // Wait for and click the "Show transcript" button
        var showTranscriptButton = wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.SHOW_TRANSCRIPT_BUTTON));
        List<String> contentTexts = new ArrayList<>();

        if (showTranscriptButton.isDisplayed()) {
            showTranscriptButton.click();
            moveSeekBarAtTheEndOfTheVideo();
            Thread.sleep(2000); // Wait for transcript to load

            // Scroll through the transcript RecyclerView and log items
            WebElement recyclerView = driver.findElement(YouTubeLocators.TRANSCRIPT_RECYCLER_VIEW);
            int X = recyclerView.getLocation().getX() + recyclerView.getSize().getWidth() / 2;
            int Y = recyclerView.getLocation().getY() + (int) (recyclerView.getSize().getHeight() * 0.8);
            int Z = recyclerView.getLocation().getY() + (int) (recyclerView.getSize().getHeight() * 0.2);

            boolean hasMoreToScroll = true;
            int scrollAttempts = 0;

            while (hasMoreToScroll && scrollAttempts < 10) {
                try {
                    List<WebElement> currentChildElements = recyclerView.findElements(YouTubeLocators.TRANSCRIPT_ITEM);

                    for (WebElement childElement : currentChildElements) {
                        String contentDesc = childElement.getAttribute("content-desc");
                        if (contentDesc != null && !contentDesc.isEmpty()) {
                            System.out.println("Content-desc: " + contentDesc);
                            contentTexts.add(contentDesc);
                        }
                    }

                    // Perform scroll action
                    new TouchAction<>(driver).press(PointOption.point(X, Y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(X, Z)).release().perform();

                    Thread.sleep(2000);

                    // Check if new items loaded
                    List<WebElement> newChildElements = recyclerView.findElements(YouTubeLocators.TRANSCRIPT_ITEM);
                    if (newChildElements.size() == currentChildElements.size()) {
                        hasMoreToScroll = false;
                    }

                } catch (StaleElementReferenceException e) {
                    System.out.println("Caught StaleElementReferenceException, retrying...");
                }

                scrollAttempts++;
            }

            if (scrollAttempts >= 10) {
                System.out.println("Reached maximum scroll attempts.");
            }
        } else {
            System.out.println("Transcript button is not displayed.");
        }

        // Close the transcript view
        driver.findElement(YouTubeLocators.CLOSE_BUTTON).click();
    }
    public boolean scrollUntilVideoFound(String targetVideoTitle, int maxScrollAttempts) {
        Set<String> seenTitles = new HashSet<>();
        int scrollAttempts = 0;

        while (scrollAttempts < maxScrollAttempts) {
            try {
                // Get current screen size
                Dimension size = driver.manage().window().getSize();

                // Find all video elements currently visible
                List<WebElement> videoElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
YouTubeLocators.VIDEO_PLAY_BUTTON
                ));

                // Check each video title
                for (WebElement video : videoElements) {
                    try {
                        String accessibilityId = video.getAttribute("content-desc");

                        String title  = accessibilityId.replace("Accessibility ID: ", "").replace(" - play video", "").split(" - ")[0];

                        System.out.println("Checking video: " + title); // Debug log

                        // Skip if we've seen this title before
                        if (!seenTitles.add(title)) {
                            continue;
                        }

                        // Check if this is our target video
                        if (title.toLowerCase().contains(targetVideoTitle.toLowerCase())) {
                            System.out.println("Found target video: " + title); // Debug log
                            try {
                                WebElement clickableVideo = wait.until(ExpectedConditions.elementToBeClickable(video));
                                clickableVideo.click();
                                System.out.println("Successfully clicked the video"); // Debug log
                                saveVideoName(title);
                                return true;
                            } catch (Exception e) {
                                System.out.println("Error clicking video: " + e.getMessage()); // Debug log
                                // Try finding the parent container and click that instead
                                try {
                                    WebElement parentContainer = video.findElement(By.xpath("./.."));
                                    parentContainer.click();
                                    System.out.println("Clicked parent container instead"); // Debug log
                                    return true;
                                } catch (Exception e2) {
                                    System.out.println("Error clicking parent container: " + e2.getMessage()); // Debug log
                                }
                            }
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale element encountered, continuing to next"); // Debug log
                        continue;
                    }
                }

                // Perform a slow scroll
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence sequence = new Sequence(finger, 0);

                // Calculate scroll positions
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.7);  // Start at 70% of screen height
                int endY = (int) (size.height * 0.3);    // End at 30% of screen height

                // Create smooth scroll action
                sequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

                // Add multiple small moves for smoother scrolling
                int steps = 10;
                int yStep = (endY - startY) / steps;

                for (int i = 1; i <= steps; i++) {
                    sequence.addAction(finger.createPointerMove(
                            Duration.ofMillis(100),
                            PointerInput.Origin.viewport(),
                            startX,
                            startY + (yStep * i)
                    ));
                }

                sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                // Perform the scroll
                driver.perform(Arrays.asList(sequence));

                System.out.println("Performed scroll attempt " + (scrollAttempts + 1)); // Debug log

                // Wait for content to load
                try {
                    Thread.sleep(1500); // Increased wait time to ensure content loads
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }

                scrollAttempts++;

            } catch (Exception e) {
                System.out.println("Error during scroll operation: " + e.getMessage()); // Debug log
                scrollAttempts++;
            }
        }

        System.out.println("Video not found after " + maxScrollAttempts + " attempts"); // Debug log
        return false;
    }
    public void swipeLeftFromShareButton() {
        var shareButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.SHARE_BUTTON));

        int startX = shareButton.getRect().getX() + (shareButton.getRect().getWidth() / 2); // Center X
        int startY = shareButton.getRect().getY() + (shareButton.getRect().getHeight() / 2); // Center Y

        int endX = startX - 500; // Swipe left distance
        int endY = startY;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

    // Get video name and save to file
    public void getVideoName() {
        try {
            WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.VIDEO_PLAY_BUTTON));
            String accessibilityId = playButton.getAttribute("content-desc");

            if (accessibilityId != null && !accessibilityId.isEmpty()) {
                String videoTitle = accessibilityId.replace("Accessibility ID: ", "").replace(" - play video", "").split(" - ")[0];

                System.out.println("Video Title: " + videoTitle);

                saveVideoName(videoTitle);
            } else {
                System.out.println("Content description is not available.");
            }
        } catch (Exception e) {
            System.err.println("Error while retrieving video name: " + e.getMessage());
        }
    }

    private static void saveVideoName(String videoTitle) throws IOException {
        File file = new File(YouTubeLocators.TRANSCRIPT_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(videoTitle + "\n");
            System.out.println("Video title written to " + YouTubeLocators.TRANSCRIPT_FILE);
        } catch (IOException e) {
            System.err.println("Error while writing to the file: " + e.getMessage());
        }
    }

    public void searchForDesiredUsername(String desiredUsername) throws InterruptedException {
        Set<String> uniqueDescriptions = new HashSet<>();
        WebElement engagementPanel = driver.findElement(YouTubeLocators.ENGAGEMENT_PANEL);

        // Calculate scroll positions
        int startX = engagementPanel.getLocation().getX() + engagementPanel.getSize().getWidth() / 2;
        int startY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.8);
        int endY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.2);

        boolean hasMoreToScroll = true;

        while (hasMoreToScroll) {
            // Find elements containing usernames
            List<WebElement> elements = engagementPanel.findElements(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"@\")"));

            for (WebElement el : elements) {
                String description = el.getAttribute("contentDescription");
                if (uniqueDescriptions.add(description)) {
                    System.out.println("Unique Username Found: " + description);

                    if (description.equals(desiredUsername)) {
                        System.out.println("Desired Username Found: " + description);
                        WebElement parentElement = driver.findElement(AppiumBy.xpath(String.format(YouTubeLocators.USERNAME_DESCRIPTION_TEMPLATE, desiredUsername)));
                        parentElement.click();
                        Thread.sleep(8000);
                        hasMoreToScroll = false;
                        break;
                    }
                }
            }

            if (hasMoreToScroll) {
                // Scroll using W3C Actions
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence scroll = new Sequence(finger, 0);

                scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                scroll.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
                scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(List.of(scroll));

                // Pause briefly after the scroll
                Thread.sleep(1000);
            }
        }
    }

    // Reply to a comment
    public void replyComment(String replyText) {
        var replyField = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.REPLY_FIELD));
        replyField.click();
        replyField.sendKeys(replyText);

        var sendCommentButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.SEND_COMMENT_BUTTON));
        sendCommentButton.click();
    }

    // Like a comment
    public void likeComment() {
        var likeButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.LIKE_COMMENT_BUTTON));
        likeButton.click();
    }

    // Add a video to a playlist
    public void addToPlaylist(String playlistTitle, String playlistVisibility) {
        try {
            // Locate and click the "Save to playlist" button
            WebElement saveToPlaylistButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.SAVE_TO_PLAYLIST_BUTTON));
            saveToPlaylistButton.click();
            clickDoneButton();
            // Wait briefly for the UI to update
            Thread.sleep(2000);

            // Check if the "Save to playlist" button is still visible
            if (isElementVisible(YouTubeLocators.SAVE_TO_PLAYLIST_BUTTON)) {
                saveToPlaylistButton.click();
                System.out.println("Clicked 'Save to playlist' button again.");
            } else if (isElementVisible(YouTubeLocators.SAVED_BUTTON)) {
                System.out.println("Video already added to playlist.");
                return;
            }

            // Check if the playlist already exists
            if (isPlaylistAvailable(playlistTitle)) {
                clickDoneButton(); // Click the "Done" button after adding to existing playlist
                return;
            }

            // If playlist doesn't exist, create a new one
            createNewPlaylist(playlistTitle, playlistVisibility);
            clickDoneButton(); // Click the "Done" button after creating a new playlist
            System.out.println("Created and added video to new playlist: " + playlistTitle);

        } catch (Exception e) {
            System.out.println("An error occurred while adding to the playlist: " + e.getMessage());
        }
    }

    // Helper method to check element visibility
    private boolean isElementVisible(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to check if playlist is available and add the video
    private boolean isPlaylistAvailable(String playlistTitle) {
        try {
            WebElement parentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator(YouTubeLocators.playlistTitleLocator(playlistTitle))));
            WebElement checkbox = parentElement.findElement(AppiumBy.xpath(".//android.widget.ImageView[1]"));

            if (!checkbox.isSelected()) {
                parentElement.click();
                System.out.println("Added video to existing playlist: " + playlistTitle);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Playlist not found: " + playlistTitle);
            return false;
        }
    }

    // Helper method to create a new playlist
    private void createNewPlaylist(String playlistTitle, String playlistVisibility) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.PLAYLIST_BOTTOM_SHEET));
        WebElement plusNewPlaylistButton = driver.findElement(YouTubeLocators.CREATE_NEW_PLAYLIST_BUTTON);
        plusNewPlaylistButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.PLAYLIST_TITLE_FIELD));
        WebElement titleEditBox = driver.findElement(YouTubeLocators.PLAYLIST_TITLE_FIELD);
        titleEditBox.sendKeys(playlistTitle);

        WebElement playlistDropDown = driver.findElement(YouTubeLocators.PLAYLIST_VISIBILITY_DROPDOWN);
        playlistDropDown.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.PLAYLIST_BOTTOM_SHEET));
        WebElement playlistType = driver.findElement(AppiumBy.androidUIAutomator(YouTubeLocators.playlistVisibilityLocator(playlistVisibility)));
        playlistType.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.PLAYLIST_BOTTOM_SHEET));
        WebElement createButton = driver.findElement(YouTubeLocators.PLAYLIST_CREATE_BUTTON);
        createButton.click();
    }

    // Helper method to click the "Done" button
    private void clickDoneButton() {
        try {
            WebElement doneButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.DONE_BUTTON));
            doneButton.click();
            System.out.println("Clicked 'Done' button.");
        } catch (Exception e) {
            System.out.println("Unable to click 'Done' button: " + e.getMessage());
        }
    }


    public String getVideoCaption() {
        try {
            // Wait until the play button is clickable
            WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(YouTubeLocators.VIDEO_PLAY_BUTTON));

            // Retrieve the content description (accessibility ID)
            String accessibilityId = playButton.getAttribute("content-desc");

            if (accessibilityId != null && !accessibilityId.isEmpty()) {
                // Extract the video title
                String videoTitle = accessibilityId.replace("Accessibility ID: ", "").replace(" - play video", "").split(" - ")[0];

                System.out.println("Video Title: " + videoTitle);
                return videoTitle;
            } else {
                System.out.println("Content description is not available.");
            }
        } catch (Exception e) {
            System.err.println("Error while retrieving video name: " + e.getMessage());
        }

        // Return an empty string if the video name cannot be retrieved
        return "";
    }


    public String getTranscriptAsString() throws InterruptedException {
        try {
            // Click the first item in the watch list
            var watchList = driver.findElement(YouTubeLocators.WATCH_LIST);
            watchList.findElement(YouTubeLocators.WATCH_LIST_ITEM).click();

            // Scroll the content area to ensure the "Show transcript" button is visible
            WebElement content = driver.findElement(YouTubeLocators.CONTENT_AREA);
            int startX = content.getLocation().getX() + content.getSize().getWidth() / 2;
            int startY = content.getLocation().getY() + (int) (content.getSize().getHeight() * 0.8);
            int endY = content.getLocation().getY() + (int) (content.getSize().getHeight() * 0.1);

            for (int i = 0; i < 2; i++) {
                performScroll(startX, startY, startX, endY);
            }

            // Wait for and click the "Show transcript" button
            var showTranscriptButton = wait.until(ExpectedConditions.visibilityOfElementLocated(YouTubeLocators.SHOW_TRANSCRIPT_BUTTON));

            if (!showTranscriptButton.isDisplayed()) {
                System.out.println("Transcript button is not displayed.");
                return "";
            }

            showTranscriptButton.click();
            moveSeekBarAtTheEndOfTheVideo();
            Thread.sleep(2000); // Wait for transcript to load

            // Scroll through the transcript RecyclerView and collect transcript text
            WebElement recyclerView = driver.findElement(YouTubeLocators.TRANSCRIPT_RECYCLER_VIEW);
            int X = recyclerView.getLocation().getX() + recyclerView.getSize().getWidth() / 2;
            int Y = recyclerView.getLocation().getY() + (int) (recyclerView.getSize().getHeight() * 0.8);
            int Z = recyclerView.getLocation().getY() + (int) (recyclerView.getSize().getHeight() * 0.2);

            StringBuilder transcriptBuilder = new StringBuilder();
            boolean hasMoreToScroll = true;
            int scrollAttempts = 0;

            while (hasMoreToScroll && scrollAttempts < 10) {
                try {
                    List<WebElement> currentChildElements = recyclerView.findElements(YouTubeLocators.TRANSCRIPT_ITEM);

                    for (WebElement childElement : currentChildElements) {
                        String contentDesc = childElement.getAttribute("content-desc");
                        if (contentDesc != null && !contentDesc.isEmpty()) {
                            transcriptBuilder.append(contentDesc).append("\n");
                        }
                    }

                    // Perform scroll action
                    performScroll(X, Y, X, Z);

                    Thread.sleep(2000);

                    // Check if new items loaded
                    List<WebElement> newChildElements = recyclerView.findElements(YouTubeLocators.TRANSCRIPT_ITEM);
                    if (newChildElements.size() == currentChildElements.size()) {
                        hasMoreToScroll = false;
                    }

                } catch (StaleElementReferenceException e) {
                    System.out.println("Caught StaleElementReferenceException, retrying...");
                }

                scrollAttempts++;
            }

            if (scrollAttempts >= 10) {
                System.out.println("Reached maximum scroll attempts.");
            }

            // Close the transcript view
//            driver.findElement(YouTubeLocators.CLOSE_BUTTON).click();

            return transcriptBuilder.toString().trim();
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving the transcript: " + e.getMessage());
            return ""; // Return an empty string in case of failure
        }
    }

    public void closeTransciptWindow() {
        driver.findElement(YouTubeLocators.CLOSE_BUTTON).click();
    }

    private void performScroll(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }


}
