package TikTok_Tests;

import base.BaseTest2;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TikTokHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TikTokTestUserBio extends BaseTest2 {
    private TikTokHelper tikTokHelper;
    private String searchVideo;
    private String comment;

    @BeforeClass
    public void initHelper() throws IOException {
        tikTokHelper = new TikTokHelper(driver);
        loadDataFromJson("src/test/java/configJSON/tiktokData.json");
    }

    private void loadDataFromJson(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        searchVideo = jsonObject.getString("searchVideo");
        comment = jsonObject.getString("comment");
    }

    @Test
    public void UserBio() throws InterruptedException {
        tikTokHelper.goToProfile();
        tikTokHelper.editProfile();
        tikTokHelper.clickOnBio();
        tikTokHelper.activateBioField("life is awesome!!!");
        tikTokHelper.save();
//        tikTokHelper.clickOnUserNameField();
//        tikTokHelper.inPutUsername("desired");
//        tikTokHelper.save();
//        tikTokHelper.clickOnNameField();
//        tikTokHelper.inPutUsername("my Name");
//        tikTokHelper.save();
        Thread.sleep(3000);

    }
}





