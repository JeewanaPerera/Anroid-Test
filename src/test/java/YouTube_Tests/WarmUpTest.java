package YouTube_Tests;

import base.BaseTest;
import utils.YouTubeHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class WarmUpTest extends BaseTest {
    private YouTubeHelper youTubeHelper;
    private String searchVideo;
    private String comment;

    @BeforeClass
    public void initHelper() throws IOException {
        // Initialize Appium helper after the driver is set up
        youTubeHelper = new YouTubeHelper(driver);

        // Load data from JSON file
        loadDataFromJson();
    }

    private void loadDataFromJson() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/test/javaconfigJSON/youtubeConfig.json")));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray videoNamesArray = jsonObject.getJSONArray("videoNames");
        List<String> videoNames = IntStream.range(0, videoNamesArray.length())
                .mapToObj(videoNamesArray::getString)
                .toList();

        Random rand = new Random();
        int randomIndex = rand.nextInt(videoNames.size());
        searchVideo = videoNames.get(randomIndex);

        System.out.println("Randomly selected video name: " + searchVideo);
        comment = jsonObject.getString("comment");
        System.out.println("Comment: " + comment);
    }

    @Test
    public void WarmUpTest() throws InterruptedException {
        youTubeHelper.searchAuthor("Mr Beast");
        youTubeHelper.goToAuthor();
        youTubeHelper.goToAuthorLatestVideos();
        youTubeHelper.selectFirstVideoPlayButton();
        Thread.sleep(5000);
        youTubeHelper.navigateToCommentsSection();
        youTubeHelper.goToNewestCommentsTab();
        Thread.sleep(3000);
        youTubeHelper.goToTopCommentsTab();
    }

    @Test
    public void subscribeLikeVideoTest() throws InterruptedException {
        youTubeHelper.searchVideo(searchVideo);
        youTubeHelper.clickFirstSearchResult();
        Thread.sleep(5000);
        youTubeHelper.subscribe();
        youTubeHelper.likeVideo();
        youTubeHelper.saveToWatchLater();
        youTubeHelper.playRandomSuggestedVideo();
        Thread.sleep(5000);
    }


    @Test
    public  void replyToCommentTest() throws InterruptedException {
        youTubeHelper.searchAuthor("Mr Beast");
        youTubeHelper.goToAuthor();
        youTubeHelper.goToAuthorLatestVideos();
        youTubeHelper.selectFirstVideoPlayButton();
        Thread.sleep(5000);
        youTubeHelper.navigateToCommentsSection();
        youTubeHelper.goToNewestCommentsTab();
        youTubeHelper.replyToComment("Great Video");
    }

    @Test
    public void subscribeToChannelFromTheVideoTest(){
        youTubeHelper.searchAuthor("Mr Beast");
        youTubeHelper.goToAuthor();
        youTubeHelper.goToAuthorLatestVideos();
        youTubeHelper.selectFirstVideoPlayButton();
        youTubeHelper.subscribeToChannel("MrBeast");
    }
}
