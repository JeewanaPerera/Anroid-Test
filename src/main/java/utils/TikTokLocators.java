package utils;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class TikTokLocators {
    public static final By MAIN_SEARCH_BUTTON = AppiumBy.xpath("(//android.widget.ImageView)[8]");
    public static final By SEARCH_INPUT = AppiumBy.className("android.widget.EditText");
    public static final By SEARCH_BUTTON = AppiumBy.className("android.widget.Button");
    public static final By VIDEOS_TAB_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().text(\"Videos\")");
    public static final By USERS_TAB_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().text(\"Users\").instance(0)");
    public static final By FIRST_USER = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(4)");
    public static final By LAST_VIDEO = AppiumBy.xpath("(//android.widget.ImageView[@resource-id='com.zhiliaoapp.musically:id/cover'])[1]");
    public static final By CAPTION = AppiumBy.className("com.bytedance.tux.input.TuxTextLayoutView");
    public static final By LIKE_BUTTON = AppiumBy.accessibilityId("Like");
    public static final By COMMENT_BUTTON = AppiumBy.xpath("//android.widget.Button[contains(@content-desc, 'Read or add comments')]");
    public static final By COMMENT_FIELD = AppiumBy.className("android.widget.EditText");
    public static final By EXPLORE_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().text(\"Explore\")");
    public static final By VIDEO_COVERS = AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.zhiliaoapp.musically:id/cover']");
    public static final By SHARE_BUTTON = AppiumBy.xpath("//*[contains(@content-desc, 'Share video')]");
//    public static final By REPOST_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().text(\"Repost\")");
    public static final By REPOST_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.zhiliaoapp.musically:id/t1x\")");

    public static final By VIDEO_TITLE = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.zhiliaoapp.musically:id/title\")");
    public static final By FOLLOW_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().text(\"Follow\")");

    public static final By PROFILE_BUTTON = AppiumBy.xpath("//android.widget.FrameLayout[@content-desc=\"Profile\"]/android.widget.ImageView");
    public static final By FOLLOWERS_TAB = AppiumBy.androidUIAutomator("new UiSelector().text(\"Following\")");
    public static final By FOLLOWING_ELEMENTS = AppiumBy.androidUIAutomator("text(\"Following\")");
    public static final By ACCOUNT_ID = AppiumBy.id("com.zhiliaoapp.musically:id/mmq");

    // Buttons and fields
    public static final By MAIN_UPLOAD_BUTTON = AppiumBy.xpath("//android.widget.Button[@content-desc='Create']/android.widget.ImageView");
    public static final By NEXT_BUTTON = AppiumBy.xpath("(//android.widget.Button[@text='Next'])[2]");
    public static final By SECOND_NEXT_BUTTON = AppiumBy.xpath("(//android.widget.TextView[@text='Next'])");
    public static final By POST_BUTTON = AppiumBy.accessibilityId("Post");
    public static final By EDIT_PROFILE_BUTTON = AppiumBy.accessibilityId("Edit profile");
    public static final By NAME_FIELD = AppiumBy.xpath("//*[contains(@content-desc, 'Name')]");
    public static final By USERNAME_FIELD = AppiumBy.xpath("//*[contains(@content-desc, 'Username')]");
    public static final By INPUT_FIELD = AppiumBy.className("android.widget.EditText");
    public static final By SAVE_BUTTON = AppiumBy.accessibilityId("Save");
    public static final By BIO_FIELD = AppiumBy.xpath("//*[contains(@content-desc, 'Bio')]");

    // Coordinates for open gallery and choosing the newest video
    public static final int OPEN_GALLERY_X = 890;
    public static final int OPEN_GALLERY_Y = 1500;
    public static final int CLICK_LATEST_VIDEO_X = 150;
    public static final int CLICK_LATEST_VIDEO_Y = 520;

    // Coordinates for clicking search button
    public static final int SEARCH_BUTTON_X = 998;
    public static final int SEARCH_BUTTON_Y = 168;
}
