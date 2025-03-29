package YouTube_Tests;

import base.BaseTest;
import utils.YouTubeConfigHelper;
import utils.YouTubeHelper;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AddToPlaylistTest extends BaseTest {
    private YouTubeHelper youTubeHelper;
    private YouTubeConfigHelper configHelper;


    @BeforeClass
    public void initHelper() throws IOException {
        youTubeHelper = new YouTubeHelper(driver);
        configHelper = new YouTubeConfigHelper("src/test/java/configJSON/youtubeConfig.json");
    }

    @Test
    public void addToPlaylistTest() throws InterruptedException {
        List<String> influencers = configHelper.getInfluencerNames();
        youTubeHelper.searchAuthor(influencers.getFirst());
        youTubeHelper.goToAuthor();
        Thread.sleep(3000);
        youTubeHelper.goToAuthorLatestVideos();
        youTubeHelper.selectFirstVideoPlayButton();

        Thread.sleep(6000);
        youTubeHelper.swipeLeftFromShareButton();
        youTubeHelper.addToPlaylist(influencers.getFirst(), "Private");

    }
}
