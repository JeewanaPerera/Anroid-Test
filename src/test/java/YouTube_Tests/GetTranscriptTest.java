package YouTube_Tests;

import base.BaseTest;
import utils.YouTubeHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GetTranscriptTest  extends BaseTest {
    private YouTubeHelper youTubeHelper;
    private String searchVideo;
    private String comment;

    @BeforeClass
    public void initHelper() throws IOException {
        youTubeHelper = new YouTubeHelper(driver);
        loadDataFromJson();
    }

    private void loadDataFromJson() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/main/parentData.json")));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray videoNamesArray = jsonObject.getJSONArray("parentKeyWords");
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
    public void getTranscriptTest() throws InterruptedException, IOException {
        youTubeHelper.searchVideo(searchVideo);
        youTubeHelper.getVideoName();
        youTubeHelper.clickFirstSearchResult();
        Thread.sleep(5000);
        youTubeHelper.showAndLogTranscripts();
    }
}
