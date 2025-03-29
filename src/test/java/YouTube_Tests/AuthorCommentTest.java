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
import java.util.stream.IntStream;

public class AuthorCommentTest extends BaseTest {
    private YouTubeHelper youTubeHelper;
    private String searchAuthor;
    private String comment;

    private void loadDataFromJson() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/main/users.json")));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray videoNamesArray = jsonObject.getJSONArray("authorNames");
        List<String> authorNames = IntStream.range(0, videoNamesArray.length())
                .mapToObj(videoNamesArray::getString)
                .toList();


        for (String authorName : authorNames) {
            searchAuthor = authorName;
        }

        System.out.println("Randomly selected author name: " + searchAuthor);
        comment = jsonObject.getString("comment");
        System.out.println("Comment: " + comment);
    }

    @BeforeClass
    public void initHelper() throws IOException {
        youTubeHelper = new YouTubeHelper(driver);
        loadDataFromJson();
    }


    @Test
    public void likeAddToPlaylistTest() throws InterruptedException {
        youTubeHelper.searchAuthor(searchAuthor);
        youTubeHelper.goToAuthor();
        Thread.sleep(3000);

        youTubeHelper.subscribe();
        Thread.sleep(3000);
        youTubeHelper.goToAuthorLatestVideos();
        Thread.sleep(2000);

        youTubeHelper.getVideoName();
        youTubeHelper.selectFirstVideoPlayButton();
        Thread.sleep(5000);

        youTubeHelper.likeVideo();
        youTubeHelper.swipeLeftFromShareButton();
        youTubeHelper.saveToWatchLater();
        Thread.sleep(7000);

        youTubeHelper.addComment(comment);
        Thread.sleep(5000);
    }
}
