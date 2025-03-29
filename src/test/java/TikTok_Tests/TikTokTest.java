package TikTok_Tests;

import base.BaseTest2;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TikTokHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class  TikTokTest extends BaseTest2 {
    private TikTokHelper tikTokHelper;
    private String searchVideo;
    private String comment;

    @BeforeClass
    public void initHelper() throws IOException {
        // Initialize Appium helper after the driver is set up
        tikTokHelper = new TikTokHelper(driver);
        // Load data from JSON file
        loadDataFromJson("src/test/java/configJSON/tiktokData.json");
    }

    private void loadDataFromJson(String filePath) throws IOException {
        // Read the JSON file as a string
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        // Parse the JSON data
        searchVideo = jsonObject.getString("searchVideo");
        comment = jsonObject.getString("comment");
    }

    @Test
    public void BrowsingTikTok() throws InterruptedException {
        //Swipe and watch RANDOM
        tikTokHelper.swipeWatchLike(TikTokHelper.getRandomNumberBetween(1, 7));
        //click on mains Serach button
        tikTokHelper.clickOnSearchCoordinates();
        //activate search field and input search value
        tikTokHelper.inputSearchValue(searchVideo);
        //search
        tikTokHelper.searchVideo();
//        Thread.sleep(1500);
        tikTokHelper.selectUsersTab();
//        Thread.sleep(1500);
        //select first user
        tikTokHelper.selectFirstUser();
        //open users videos
        tikTokHelper.openLastVideo();
        //print caption
        tikTokHelper.printCaptions();
        //like
        tikTokHelper.likeUnlike();
        //watch random videos of user like
        tikTokHelper.swipeWatchLike(TikTokHelper.getRandomNumberBetween(1, 7));
        //click on comment button
        tikTokHelper.clickOnComment();
        //activate comment field
        tikTokHelper.activateCommentField();
        //leaveComment
        tikTokHelper.inputComment("Great!");
        //comment
        tikTokHelper.postComment();
    }
}





