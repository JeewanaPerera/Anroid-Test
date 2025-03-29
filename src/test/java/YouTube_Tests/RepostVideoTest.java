package YouTube_Tests;

import base.BaseTest;
import utils.YouTubeHelper;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RepostVideoTest extends BaseTest {
    private YouTubeHelper youTubeHelper;
    private String transcriptText;

    @BeforeClass
    public void initHelper() throws IOException {
        youTubeHelper = new YouTubeHelper(driver);
        loadTranscriptFromFile();
    }

    private void loadTranscriptFromFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("transcript.txt"));
        // Check if the file is not empty
        if (!lines.isEmpty()) {
            for (String line : lines) {
                System.out.println("Loaded transcript line: " + line);
                transcriptText = line;
            }
        } else {
            System.out.println("The transcript file is empty.");
        }
    }


    @Test
    public void repostVideoTest() throws InterruptedException {
        if (transcriptText != null && !transcriptText.isEmpty()) {
            youTubeHelper.searchVideo(transcriptText);
            youTubeHelper.clickFirstSearchResult();
        } else {
            youTubeHelper.searchAuthor("MrBeast");
            youTubeHelper.goToAuthor();
            Thread.sleep(3000);
            youTubeHelper.goToAuthorLatestVideos();
            youTubeHelper.selectFirstVideoPlayButton();
        }
        Thread.sleep(6000);
        youTubeHelper.createPost("Great Video");
    }

}
