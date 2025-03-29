package YouTube_Tests;

import base.BaseTest;
import org.json.JSONObject;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.OpenAIClient;
import utils.YouTubeConfigHelper;
import utils.YouTubeHelper;
import utils.YouTubeLocators;

import java.io.IOException;
import java.util.List;

public class YoutubeCommentVideo extends BaseTest {
    private String videoName = System.getProperty("videoName","I Spent 100 Hours Inside The Pyramids!");

    private YouTubeHelper youTubeHelper;
    private YouTubeConfigHelper configHelper;
    private OpenAIClient openAIGenerator;
    private String parentVideoCaption;
    private String parentVideoDescription;
    private String parentVideo;

    private String currentVideoTranscript;
    private String currentVideoName;
    private String commentText;

    @BeforeClass
    public void initHelper() throws IOException {
        openAIGenerator = new OpenAIClient();
        // Initialize Appium helper after the driver is set up
        youTubeHelper = new YouTubeHelper(driver);
        configHelper = new YouTubeConfigHelper("src/test/java/configJSON/youtubeConfig.json");
    }

    @Test
    public void getTranscriptTest() throws InterruptedException, IOException {
//        List<String> influencers = configHelper.getInfluencerNames();
        youTubeHelper.searchVideo(videoName/*influencers.getFirst()*/);

        youTubeHelper.scrollUntilVideoFound(videoName,1);


        youTubeHelper.getVideoName();

        currentVideoName = youTubeHelper.getVideoCaption();
        parentVideoDescription = configHelper.getParentVideoDescription();



        Thread.sleep(2000);

        commentText = openAIGenerator.sendOpenAIRequest(configHelper.getApiKey(), configHelper.getModel(), parentVideoDescription, currentVideoName, currentVideoName);
        youTubeHelper.addComment(commentText);
        System.out.println("Generated Comment:");
        System.out.println("==================");
        System.out.println(commentText);
        System.out.println("==================");

        youTubeHelper.addComment(commentText);

    }


}