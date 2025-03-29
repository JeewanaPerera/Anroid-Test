package TikTok_Tests;

import base.BaseTest2;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TikTokHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class TikTokRepost extends BaseTest2 {
    private TikTokHelper tikTokHelper;
    private String searchVideo;
    private static final Logger LOGGER = Logger.getLogger(TikTokHelper.class.getName());

    @BeforeClass
    public void initHelper() throws IOException {
        tikTokHelper = new TikTokHelper(driver);
        loadDataFromJson("src/test/java/configJSON/tiktokData.json");
    }
    private void loadDataFromJson(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);

        searchVideo = jsonObject.getString("searchVideo");
    }
    @Test
    public void repost() throws InterruptedException {


        tikTokHelper.clickOnSearchCoordinates();
        //activate search field and input search value
        tikTokHelper.inputSearchValue(searchVideo);
        //search
        tikTokHelper.searchVideo();

        tikTokHelper.selectUsersTab();
        //select first user
        tikTokHelper.selectFirstUser();
        Thread.sleep(1000);
        tikTokHelper.openLastVideo();
        Thread.sleep(1000);//Sleep duration should be randomized programmatically
        tikTokHelper.likeIfNotLiked();
        Thread.sleep(1000);
        tikTokHelper.clickOnShare();
        Thread.sleep(500);
        tikTokHelper.repost();
        LOGGER.info("After clicking repost");
        Thread.sleep(2000);

    }
}
