package noobsdev.mineland.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GsonUtility {

    private static final Gson gson = new Gson();

    public static String serialize(String gameName, String gameAuthor, String gameId, int gameOnline, int gamePoints) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", gameName);
        jsonObject.addProperty("author", gameAuthor);
        jsonObject.addProperty("id", gameId);
        jsonObject.addProperty("online", gameOnline);
        jsonObject.addProperty("points", gamePoints);
        return gson.toJson(jsonObject);
    }
}
