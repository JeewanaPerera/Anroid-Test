package YouTube_Tests;

import base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.YouTubeConfigHelper;
import utils.YouTubeHelper;

import java.io.IOException;
import java.util.List;

public class YoutubeReplyCommentTest extends BaseTest {
    private YouTubeHelper youTubeHelper;
    private YouTubeConfigHelper configHelper;


    @BeforeClass
    public void initHelper() throws IOException {
        youTubeHelper = new YouTubeHelper(driver);
        configHelper = new YouTubeConfigHelper("src/test/java/configJSON/youtubeConfig.json");
    }

    @Test
    public void likeComment() throws InterruptedException, IOException {
        List<String> influencers = configHelper.getInfluencerNames();
        youTubeHelper.searchAuthor(influencers.getFirst());
        youTubeHelper.goToAuthor();
        Thread.sleep(3000);
        youTubeHelper.goToAuthorLatestVideos();
        youTubeHelper.selectFirstVideoPlayButton();


        Thread.sleep(5000);
        youTubeHelper.navigateToCommentsSection();
        youTubeHelper.goToNewestCommentsTab();
        Thread.sleep(3000);
        youTubeHelper.goToTopCommentsTab();
        Thread.sleep(5000);

        youTubeHelper.searchForDesiredUsername("@yunna271");
        Thread.sleep(5000);
        youTubeHelper.replyComment("Agree!!");
        youTubeHelper.likeComment();

    }
}
