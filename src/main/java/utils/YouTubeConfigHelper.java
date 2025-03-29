package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeConfigHelper {
    private JSONObject jsonData;

    public YouTubeConfigHelper(String filePath) {
        loadJsonFromFile(filePath);
    }

    // Load JSON data from a file
    private void loadJsonFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonData = new JSONObject(tokener);
        } catch (IOException e) {
            System.err.println("Failed to load JSON file: " + e.getMessage());
        }
    }

    // Method to return video names as a list
    public List<String> getVideoNames() {
        if (jsonData == null) return new ArrayList<>();
        JSONArray videoNamesArray = jsonData.getJSONArray("videoNames");
        List<String> videoNames = new ArrayList<>();
        for (int i = 0; i < videoNamesArray.length(); i++) {
            videoNames.add(videoNamesArray.getString(i));
        }
        return videoNames;
    }

    // Method to return influencer names as a list
    public List<String> getInfluencerNames() {
        if (jsonData == null) return new ArrayList<>();
        JSONArray influencerNamesArray = jsonData.getJSONArray("influencerNames");
        List<String> influencerNames = new ArrayList<>();
        for (int i = 0; i < influencerNamesArray.length(); i++) {
            influencerNames.add(influencerNamesArray.getString(i));
        }
        return influencerNames;
    }

    // Method to return the comment as a string
    public String getComment() {
        if (jsonData == null) return "";
        return jsonData.optString("comment", "");
    }

    // Method to return the parent video description as a string
    public String getParentVideoDescription() {
        if (jsonData == null) return "";
        return jsonData.optString("parentVideoDescription", "");
    }

    // Method to return the parent video name as a string
    public String getParentVideoName() {
        if (jsonData == null) return "";
        return jsonData.optString("parentVideoName", "");
    }

    // Method to return the commenter username as a string
    public String getCommenterUserName() {
        if (jsonData == null) return "";
        return jsonData.optString("commenterUserName", "");
    }

    public String getApiKey() {
        if (jsonData == null) return "";
        return jsonData.optString("apiKey", "");
    }

    public String getModel() {
        if (jsonData == null) return "";
        return jsonData.optString("model", "");
    }

}