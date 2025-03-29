package utils;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class YouTubeLocators {
    // Locators
    public static final String SUBSCRIBE_BUTTON_DESCRIPTION = "Subscribe to %s."; // Format string for dynamic channel name
    public static final By GENERIC_SUBSCRIBE_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Subscribe\")");
    public static final By GO_TO_AUTHOR_CHANNEL = AppiumBy.androidUIAutomator("new UiSelector().description(\"Go to channel\").instance(0)");
    public static final By AUTHOR_LATEST_VIDEOS_TAB = AppiumBy.androidUIAutomator("new UiSelector().text(\"VIDEOS\")");
    public static final By LATEST_TAB = AppiumBy.accessibilityId("Latest");
    public static final By REPLY_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().description(\"Reply\").instance(0)");
    public static final By REPLIES_SECTION = AppiumBy.xpath("//android.view.ViewGroup[@content-desc[contains(., 'replies')]]");
    public static final By ADD_REPLY_FIELD = AppiumBy.androidUIAutomator("new UiSelector().text(\"Add a reply...\")");
    public static final By SEARCH_BUTTON = AppiumBy.accessibilityId("Search");
    public static final By SEARCH_INPUT = AppiumBy.id("com.google.android.youtube:id/search_edit_text");
    // Search Results
    public static final By FIRST_SEARCH_RESULT = AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id=\"com.google.android.youtube:id/results\"]/android.view.ViewGroup[1]");
    public static final By FIRST_VIDEO_PLAY_BUTTON = AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id='com.google.android.youtube:id/results']" +
            "//android.view.ViewGroup[@content-desc[contains(., 'play video')]]");
    // Search result list
    public static final By SEARCH_RESULT_LIST = AppiumBy.className("android.widget.TextView");
    // Like button
    public static final By LIKE_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"like this video\")");
    // Buttons
    public static final By DISLIKE_BUTTON = AppiumBy.accessibilityId("Dislike this video");
    public static final By COMMENTS_SECTION = AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id=\"com.google.android.youtube:id/watch_list\"]/android.view.ViewGroup[4]/android.view.ViewGroup");
    public static final By CREATE_POST = AppiumBy.accessibilityId("Create post");
    public static final By SHARE_BUTTON = AppiumBy.accessibilityId("Share");
    public static final By POST_BUTTON = AppiumBy.id("com.google.android.youtube:id/post_button");
    public static final By CONTENT_EDIT_BOX = AppiumBy.className("android.widget.EditText");
    // Tabs
    public static final String COMMENTS_TAB_ACCESSIBILITY = "%s"; // Use String.format for dynamic tab names
    public static final By NEWEST_TAB = AppiumBy.accessibilityId("Newest");
    public static final By TOP_TAB = AppiumBy.accessibilityId("Top");
    // Post Bottom Sheet
    public static final By DESIGN_BOTTOM_SHEET = AppiumBy.id("com.google.android.youtube:id/design_bottom_sheet");
    // Video Play
    public static final By SUGGESTED_VIDEO_PLAY_BUTTON = AppiumBy.xpath("//android.view.ViewGroup[@content-desc[contains(., 'play video')]]/android.view.ViewGroup[1]");
    public static final By WATCH_LIST = AppiumBy.id("com.google.android.youtube:id/watch_list");
    // Save to Playlist
    public static final By SAVE_TO_PLAYLIST_BUTTON = AppiumBy.accessibilityId("Save to playlist");
    public static final By DONE_BUTTON = AppiumBy.accessibilityId("Done");
    public static final By MESSAGE = AppiumBy.id("com.google.android.youtube:id/message");
    // Comments
    public static final By ADD_COMMENT_FIELD = AppiumBy.androidUIAutomator("new UiSelector().text(\"Add a comment...\")");
    public static final By SEND_COMMENT_BUTTON = AppiumBy.accessibilityId("Send comment");
    // Engagement Panel
    public static final By ENGAGEMENT_PANEL = AppiumBy.id("com.google.android.youtube:id/engagement_panel");
    public static final String USERNAME_DESCRIPTION = "new UiSelector().descriptionContains(\"@\").instance(%d)";
    // Video player controls
    public static final By VIDEO_PLAYER = AppiumBy.accessibilityId("Video player");
    public static final By PLAY_PAUSE_BUTTON = AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.google.android.youtube:id/player_control_play_pause_replay_button']");
    public static final By TOTAL_TIME_TEXT = AppiumBy.xpath("//android.widget.TextView[@resource-id='com.google.android.youtube:id/time_bar_total_time']");
    // Seek bar
    public static final By SEEK_BAR = AppiumBy.className("android.widget.SeekBar");
    public static final By WATCH_LIST_ITEM = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\")");
    // Content
    public static final By CONTENT_AREA = AppiumBy.id("com.google.android.youtube:id/content");
    // Transcript
    public static final By SHOW_TRANSCRIPT_BUTTON = AppiumBy.accessibilityId("Show transcript");
    public static final By TRANSCRIPT_RECYCLER_VIEW = AppiumBy.className("android.support.v7.widget.RecyclerView");
    public static final By TRANSCRIPT_ITEM = AppiumBy.className("android.view.ViewGroup");
    // Close Button
    public static final By CLOSE_BUTTON = AppiumBy.accessibilityId("Close");
    // Video Play Button
    public static final By VIDEO_PLAY_BUTTON = AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@resource-id='com.google.android.youtube:id/results']" +
            "//android.view.ViewGroup[@content-desc[contains(., 'play video')]]");
    // Username Search
    public static final String USERNAME_DESCRIPTION_TEMPLATE = "//android.widget.ImageView[@content-desc=\"%s\"]/..";
    // Transcript File
    public static final String TRANSCRIPT_FILE = "transcript.txt";
    // Comments
    public static final By REPLY_FIELD = AppiumBy.androidUIAutomator("new UiSelector().text(\"Add a reply...\")");
    public static final By LIKE_COMMENT_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"like this comment\")");
    // Playlists
    public static final By SAVED_BUTTON = AppiumBy.accessibilityId("Saved");
    public static final By CREATE_NEW_PLAYLIST_BUTTON = AppiumBy.accessibilityId("Create new playlist");
    public static final By PLAYLIST_TITLE_FIELD = AppiumBy.className("android.widget.EditText");
    public static final By PLAYLIST_VISIBILITY_DROPDOWN = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Visibility\")");
    public static final By PLAYLIST_BOTTOM_SHEET = AppiumBy.id("com.google.android.youtube:id/design_bottom_sheet");
    public static final By PLAYLIST_CREATE_BUTTON = AppiumBy.accessibilityId("Create");

    // Dynamic Locators
    public static String playlistTitleLocator(String playlistTitle) {
        return "new UiSelector().descriptionContains(\"" + playlistTitle + "\")";
    }

    public static String playlistVisibilityLocator(String playlistVisibility) {
        return "new UiSelector().descriptionContains(\"" + playlistVisibility + "\")";
    }
}

