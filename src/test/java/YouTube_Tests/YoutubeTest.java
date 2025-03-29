package YouTube_Tests;

import base.BaseTest;
import utils.YouTubeHelper;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.IOException;

public class YoutubeTest extends BaseTest {
    private YouTubeHelper appiumHelper;
    @Test
    public void searchInYoutube() throws InterruptedException, IOException {
        appiumHelper = new YouTubeHelper(driver);
//        String videoName = appiumHelper.getVideoNameFromJson("src/main/file.json");
        appiumHelper.searchVideo("fz9US28MVKE");
        appiumHelper.clickFirstSearchResult();
        Thread.sleep(2000);
        appiumHelper.showAndLogTranscripts();

    }
}
