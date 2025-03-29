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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class YouTubeAppiumHelper2 {
    private final AndroidDriver driver; // Changed to AndroidDriver
    private final WebDriverWait wait;

    public YouTubeAppiumHelper2(AndroidDriver driver) { // Changed to AndroidDriver
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void subscribeToChannel(String channelName) {
        WebElement subscribeButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Subscribe to " + channelName + ".")));
        subscribeButton.click();
        System.out.println("Subscribed");
    }

    public void subscribe() {
        try {
            List<WebElement> subscribeButtons = driver.findElements(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Subscribe\")"));

            if (!subscribeButtons.isEmpty()) {
                WebElement subscribeButton = subscribeButtons.getFirst();
                subscribeButton.click();
                System.out.println("Subscribed successfully.");
            } else {
                System.out.println("Already subscribed.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to subscribe: " + e.getMessage());
        }
    }


    public void goToAuthor() {
        var channel = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().description(\"Go to channel\").instance(0)")));
        channel.click();
    }

    ///// newwwwww
    public void goToAuthorLatestVideos() {
        var videos = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().text(\"Videos\")")));
        videos.click();
        try {
            var latest = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Latest")));
            if (latest.isDisplayed()) {
                latest.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Latest Video tab is not displayed");
        }
    }


    public void replyToComment(String text) {

        try {
            var replyButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().description(\"Reply\").instance(0)")));
            if (replyButton.isDisplayed()) {
                replyButton.click();
            } else {
                var replies = driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc[contains(., 'replies')]]"));
                replies.click();
            }
            var replyField = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Add a reply...\")"))));
            replyField.click();
            replyField.sendKeys(text);

        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }


    public void searchAuthor(String authorName) {
        WebElement searchButton = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search"))
        ));
        searchButton.click();

        WebElement searchInput = driver.findElement(AppiumBy.id("com.google.android.youtube:id/search_edit_text"));
        searchInput.sendKeys(authorName);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public void searchVideo(String query) {
        WebElement searchButton = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search"))
        ));
        searchButton.click();

        WebElement searchInput = driver.findElement(AppiumBy.id("com.google.android.youtube:id/search_edit_text"));
        searchInput.sendKeys(query);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public void clickFirstSearchResult() {
        var firstResult = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id=\"com.google.android.youtube:id/results\"]/android.view.ViewGroup[1]")));
        firstResult.click();
    }

    public void selectFirstVideoPlayButton() {
        // Wait until the play button for the first video is clickable
        WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id='com.google.android.youtube:id/results']" +
                        "//android.view.ViewGroup[@content-desc[contains(., 'play video')]]")
        ));

        // Click the play button
        playButton.click();
    }


    public WebElement getAllSearchResults(Integer nthVideo) {
        var elements = driver.findElements(AppiumBy.className("android.widget.TextView"));

        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
        return elements.get(nthVideo);
    }

    public void likeVideo() {
        var likeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"like this video\")")));
        likeButton.click();
    }


    public void dislikeVideo() {
        var dislikeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Dislike this video")));
        dislikeButton.click();
    }

    public void navigateToCommentsSection() {
        var comments = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id=\"com.google.android.youtube:id/watch_list\"]/android.view.ViewGroup[4]/android.view.ViewGroup"))));
        comments.click();
    }

    public void selectCommentsTab(String tabName) {
        navigateToCommentsSection();
        var tab = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.accessibilityId(tabName))));
        tab.click();
    }

    public void goToNewestCommentsTab() {
        var newestTab = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.accessibilityId("Newest"))));
        newestTab.click();
    }

    public void goToTopCommentsTab() {
        var topTab = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.accessibilityId("Top"))));
        topTab.click();
    }

    private boolean isElementDisplayed(By locator) {
        try {
            var element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void createPost(String postDescription) throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Share")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.google.android.youtube:id/design_bottom_sheet")));
        driver.findElement(AppiumBy.accessibilityId("Create post")).click();
        Thread.sleep(2000);
        var contentEditBox = driver.findElement(AppiumBy.className("android.widget.EditText"));
        contentEditBox.sendKeys(postDescription);
        var postButton = driver.findElement(AppiumBy.id("com.google.android.youtube:id/post_button"));
        if (postButton.isEnabled()) {
            postButton.click();
        }
    }

    public void playRandomSuggestedVideo() {
        var firstSuggestedVideo = driver.findElement(AppiumBy.id("com.google.android.youtube:id/watch_list")).findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc[contains(., 'play video')]]/android.view.ViewGroup[1]"));
        firstSuggestedVideo.click();
    }

    public void saveToWatchLater() {
        try {
            // Wait until the "Save to playlist" button is clickable
            WebElement saveToPlaylistButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Save to playlist")));

            // Check if the "Save to playlist" button is displayed and interact accordingly
            if (saveToPlaylistButton.isDisplayed()) {
                saveToPlaylistButton.click();
                System.out.println("Clicked 'Save to playlist' button.");

                // Wait for either the message or the "Done" button to appear
                try {
                    // First, check if the message is displayed (i.e., the video is not saved)
                    WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.youtube:id/message")));
                    System.out.println("Message displayed: " + message.getText());
                } catch (TimeoutException e) {
                    // If message is not displayed, check if the "Done" button is visible (i.e., the video is already saved)
                    WebElement doneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Done")));
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
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred: Unable to find or click 'Save to playlist' button.");
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    public void addComment(String comment) throws InterruptedException {
        navigateToCommentsSection();
        Thread.sleep(2000);
        var addCommentField = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Add a comment...\")"))));
        addCommentField.click();
        addCommentField.sendKeys(comment);
        var sendCommentButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.accessibilityId("Send comment"))));
        sendCommentButton.click();

    }

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

    public void saveUniqueUsernamesToJson(String filePath) {
        Set<String> uniqueDescriptions = new HashSet<>(); // Track unique descriptions to avoid duplicates
        JSONArray userArray = new JSONArray(); // To store usernames as JSON array

        // Locate the engagement panel
        WebElement engagementPanel = driver.findElement(AppiumBy.id("com.google.android.youtube:id/engagement_panel"));

        // Define swipe parameters
        int startX = engagementPanel.getLocation().getX() + engagementPanel.getSize().getWidth() / 2;
        int startY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.8);
        int endY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.2);

        boolean hasMoreToScroll = true;

        while (hasMoreToScroll) {
            // Find elements containing "@" in description
            List<WebElement> elements = engagementPanel.findElements(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"@\")"));

            // Add new unique usernames to userArray
            for (WebElement el : elements) {
                String description = el.getAttribute("contentDescription");
                if (uniqueDescriptions.add(description)) {  // Only add if new and unique
                    userArray.put(description);  // Add unique username to JSON array
                    System.out.println("Unique Username Found: " + description);
                }
            }

            // Perform scroll action
            new TouchAction<>(driver)
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();

            // Stop if no new elements were found in this scroll
            hasMoreToScroll = !elements.isEmpty();
        }

        // Write the JSON array to file
        try (FileWriter file = new FileWriter(filePath)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("usernames", userArray);
            file.write(jsonObject.toString(4));  // Indented for readability
            System.out.println("Usernames saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTotalTimeOfTheVideo() {
        driver.findElement(AppiumBy.accessibilityId("Video player")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.google.android.youtube:id/player_control_play_pause_replay_button']")).click();
        WebElement timeElement = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.google.android.youtube:id/time_bar_total_time']"));
        String[] parts = timeElement.getText().split("/ ");
        String result = parts.length > 1 ? parts[1] : "";
        System.out.println(result);
    }

    public void moveSeekBarAtTheEndOfTheVideo() {
        driver.findElement(AppiumBy.accessibilityId("Video player")).click();
        WebElement seekBar = driver.findElement(AppiumBy.className("android.widget.SeekBar"));
        Rectangle bounds = seekBar.getRect();
        int seekBarWidth = bounds.getWidth();
        int seekBarStartX = bounds.getX();
        int seekBarStartY = bounds.getY();

        int targetX = seekBarStartX + seekBarWidth;

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(seekBarStartX, seekBarStartY))
                .moveTo(PointOption.point(targetX, seekBarStartY))
                .release()
                .perform();
    }


    public void showAndLogTranscripts() throws InterruptedException {
        var watchList = driver.findElement(AppiumBy.id("com.google.android.youtube:id/watch_list"));
        watchList.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\")")).click();

        WebElement content = driver.findElement(AppiumBy.id("com.google.android.youtube:id/content"));
        int startX = content.getLocation().getX() + content.getSize().getWidth() / 2;
        int startY = content.getLocation().getY() + (int) (content.getSize().getHeight() * 0.8); // Start near the bottom
        int endY = content.getLocation().getY() + (int) (content.getSize().getHeight() * 0.1);

        // Scroll a few times to ensure the transcript button is visible
        for (int i = 0; i < 2; i++) {
            new TouchAction<>(driver)
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        }

        // Wait for the "Show transcript" button to be visible and click it
        var showTranscriptButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Show transcript")));
        List<String> contentTexts = new ArrayList<>();

        if (showTranscriptButton.isDisplayed()) {
            showTranscriptButton.click();
            moveSeekBarAtTheEndOfTheVideo();
            Thread.sleep(2000); // Wait for the transcript to load

            // Locate the RecyclerView element
            WebElement recyclerView = driver.findElement(AppiumBy.className("android.support.v7.widget.RecyclerView"));

            // Define swipe parameters for RecyclerView
            int X = recyclerView.getLocation().getX() + recyclerView.getSize().getWidth() / 2;
            int Y = recyclerView.getLocation().getY() + (int) (recyclerView.getSize().getHeight() * 0.8);
            int Z = recyclerView.getLocation().getY() + (int) (recyclerView.getSize().getHeight() * 0.2);

            boolean hasMoreToScroll = true;
            int scrollAttempts = 0;

            while (hasMoreToScroll && scrollAttempts < 10) {
                try {
                    // Fetch fresh list of elements to avoid stale references
                    List<WebElement> currentChildElements = recyclerView.findElements(AppiumBy.className("android.view.ViewGroup"));

                    // Check for content-desc attribute and print it if not empty
                    for (WebElement childElement : currentChildElements) {
                        String contentDesc = childElement.getAttribute("content-desc");
                        if (contentDesc != null && !contentDesc.isEmpty()) {
                            System.out.println("Content-desc: " + contentDesc);
                            contentTexts.add(contentDesc);  // Store content for further use
                        }
                    }

                    // Perform scroll action
                    new TouchAction<>(driver)
                            .press(PointOption.point(X, Y))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                            .moveTo(PointOption.point(X, Z))
                            .release()
                            .perform();

                    // Wait for RecyclerView to load more items
                    Thread.sleep(2000);

                    // Check if new elements were loaded
                    List<WebElement> newChildElements = recyclerView.findElements(AppiumBy.className("android.view.ViewGroup"));
                    if (newChildElements.size() == currentChildElements.size()) {
                        hasMoreToScroll = false;  // No more items were loaded, we've reached the end
                    }

                } catch (StaleElementReferenceException e) {
                    System.out.println("Caught StaleElementReferenceException, retrying...");
                }

                // Increment scroll attempts to prevent infinite loop
                scrollAttempts++;
            }

            if (scrollAttempts >= 20) {
                System.out.println("Reached maximum scroll attempts.");
            }
        } else {
            System.out.println("Transcript button is not displayed");
        }

        driver.findElement(AppiumBy.accessibilityId("Close")).click();
    }

    public void downloadVideo() {
        var downloadButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Download\")")));
        downloadButton.click();
    }


    //Today
    public void swipeLeftFromShareButton() {
        // Locate and click the download button
        var shareButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Share\")")));

        // Get the location of the button
        int startX = shareButton.getRect().getX() + (shareButton.getRect().getWidth() / 2); // Center X of the button
        int startY = shareButton.getRect().getY() + (shareButton.getRect().getHeight() / 2); // Center Y of the button

        // Define the swipe end point (swiping left)
        int endX = startX - 500; // Adjust the distance to swipe left
        int endY = startY;       // Keep the same vertical position

        // Perform the swipe gesture
        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }

    public void getVideoName() {
        try {
            // Locate the element
            WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id='com.google.android.youtube:id/results']" +
                            "//android.view.ViewGroup[@content-desc[contains(., 'play video')]]")
            ));

            // Retrieve the accessibility ID (content description)
            String accessibilityId = playButton.getAttribute("content-desc");

            // Ensure accessibilityId is not null or empty
            if (accessibilityId != null && !accessibilityId.isEmpty()) {
                // Remove the "Accessibility ID: " prefix and " - play video" suffix
                String extractedText = accessibilityId.replace("Accessibility ID: ", "").replace(" - play video", "");

                // Extract the video title (first part before " - ")
                String videoTitle = extractedText.split(" - ")[0];

                // Print the video title
                System.out.println("Video Title: " + videoTitle);

                // Create a new file in the specified path
                File file = new File("transcript.txt");

                // If the file doesn't exist, create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                // Write the video title to the file
                try (FileWriter writer = new FileWriter(file, false)) { // false to overwrite file
                    writer.write(videoTitle + "\n");
                    System.out.println("Video title written to transcript.txt");
                } catch (IOException e) {
                    System.err.println("Error while writing to the file: " + e.getMessage());
                }
            } else {
                System.out.println("Content description is not available.");
            }
        } catch (Exception e) {
            System.err.println("Error while retrieving video name: " + e.getMessage());
        }
    }

    public void searchForDesiredUsername(String desiredUsername) throws InterruptedException {
        Set<String> uniqueDescriptions = new HashSet<>(); // Track unique descriptions to avoid duplicates

        // Locate the engagement panel
        WebElement engagementPanel = driver.findElement(AppiumBy.id("com.google.android.youtube:id/engagement_panel"));

        // Define swipe parameters
        int startX = engagementPanel.getLocation().getX() + engagementPanel.getSize().getWidth() / 2;
        int startY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.8);
        int endY = engagementPanel.getLocation().getY() + (int) (engagementPanel.getSize().getHeight() * 0.2);

        boolean hasMoreToScroll = true;

        while (hasMoreToScroll) {
            // Find elements containing "@" in description
            List<WebElement> elements = engagementPanel.findElements(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"@\")"));

            // Iterate through each element to check for the desired username
            for (WebElement el : elements) {
                String description = el.getAttribute("contentDescription");
                if (uniqueDescriptions.add(description)) {
                    System.out.println("Unique Username Found: " + description);

                    // Check if the desired username is found
                    if (description.equals(desiredUsername)) {
                        System.out.println("Desired Username Found: " + description);
                        WebElement parentElement = driver.findElement(AppiumBy.xpath("//android.widget.ImageView[@content-desc=\"" + desiredUsername + "\"]/.."));
                        parentElement.click();
                        Thread.sleep(8000);
                        hasMoreToScroll = false;  // Stop scrolling if desired username is found
                        break;
                    }
                }
            }

            // Perform scroll action if necessary
            if (hasMoreToScroll) {  // Only scroll if more usernames are to be found
                new TouchAction<>(driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
            }
        }
    }

    public void replyComment(String replyText) {
        var replyField = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Add a reply...\")"))));
        replyField.click();
        replyField.sendKeys(replyText);
        var sendCommentButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.accessibilityId("Send comment"))));
        sendCommentButton.click();
    }


    public void likeComment() {
        var likeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"like this comment\")")));
        likeButton.click();
    }

    public void addToPlaylist(String playlistTitle, String playlistVisibility) {
        try {
            // Locate and click the "Save to playlist" button
            WebElement saveToPlaylistButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Save to playlist")));

            if (saveToPlaylistButton.isDisplayed()) {
                saveToPlaylistButton.click();
                System.out.println("Clicked 'Save to playlist' button.");
            } else {
                WebElement savedButton = driver.findElement(AppiumBy.accessibilityId("Saved"));
                savedButton.click();
            }

            // Check if the playlist is available
            WebElement parentElement;
            WebElement checkbox;
            try {
                parentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"" + playlistTitle + "\")")
                ));
                checkbox = parentElement.findElement(AppiumBy.xpath(".//android.widget.ImageView[1]"));

                // If the playlist is available and not selected, select it
                if (!checkbox.isSelected()) {
                    parentElement.click();
                    System.out.println("Added video to existing playlist: " + playlistTitle);
                } else {
                    System.out.println("Video already in playlist: " + playlistTitle);
                }

                // Click "Done" to finalize
                driver.findElement(AppiumBy.accessibilityId("Done")).click();
                return;
            } catch (Exception e) {
                System.out.println("Playlist not found, creating a new playlist: " + playlistTitle);
            }

            // Create a new playlist if it doesn't exist
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.youtube:id/design_bottom_sheet")));
            WebElement plusNewPlaylistButton = driver.findElement(AppiumBy.accessibilityId("Create new playlist"));
            plusNewPlaylistButton.click();

            // Enter playlist title
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.youtube:id/elements_dialog_fragment_elements_view")));
            WebElement titleEditBox = driver.findElement(AppiumBy.className("android.widget.EditText"));
            titleEditBox.sendKeys(playlistTitle);

            // Set playlist visibility
            WebElement playlistDropDown = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Visibility\")"));
            playlistDropDown.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.youtube:id/design_bottom_sheet")));
            WebElement playlistType = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"" + playlistVisibility + "\")"));
            playlistType.click();

            // Create the playlist
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.youtube:id/elements_dialog_fragment_elements_view")));
            WebElement createButton = driver.findElement(AppiumBy.accessibilityId("Create"));
            createButton.click();

            System.out.println("Created and added video to new playlist: " + playlistTitle);

        } catch (Exception e) {
            System.out.println("An error occurred while adding to the playlist: " + e.getMessage());
        }
    }
}
