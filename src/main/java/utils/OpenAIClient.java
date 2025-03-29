package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAIClient {

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String CONTENT_TYPE = "application/json";

    public static String sendOpenAIRequest(String apiKey, String model, String parentVideoDescription,
                                           String targetVideoCaption, String targetVideoTranscript) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);

            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", String.format(
                    "Write a short, conversational comment comparing a description of video with the following caption '%s' " +
                            "and the following description '%s' with the parent video content with the following description '%s'. " +
                            "Format example - If you loved the surreal experience of dining 3,000 feet in the sky from @MrBeast's video, " +
                            "you'll be blown away by the equally dramatic transformation in @jackpiuggi's Not For Sale...",
                    targetVideoCaption, targetVideoTranscript, parentVideoDescription
            ));
            messages.put(message);
            requestBody.put("messages", messages);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENAI_URL))
                    .header("Content-Type", CONTENT_TYPE)
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("OpenAI API returned error: " + response.statusCode() + " - " + response.body());
            }

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray choices = jsonResponse.optJSONArray("choices");
            if (choices != null && choices.length() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject messageContent = firstChoice.optJSONObject("message");
                if (messageContent != null) {
                    return messageContent.optString("content", "");
                }
            }

            return ""; // Return an empty string if content is not found

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
