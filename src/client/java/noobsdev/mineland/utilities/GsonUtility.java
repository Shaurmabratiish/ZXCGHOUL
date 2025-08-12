package noobsdev.mineland.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

public class GsonUtility {

    private static final Gson gson = new Gson();

    public static String serializeGame(String gameName, String gameAuthor, String gameId, int gameOnline, int gamePoints) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", gameName);
        jsonObject.addProperty("author", gameAuthor);
        jsonObject.addProperty("id", gameId);
        jsonObject.addProperty("online", gameOnline);
        jsonObject.addProperty("points", gamePoints);
        return gson.toJson(jsonObject);
    }
    public static String serializeCreativeData(HashMap<String, String> data) {
        JsonObject jsonObject = new JsonObject();
        for(String key : data.keySet()) {
            jsonObject.addProperty(key, data.get(key));
        }
        return gson.toJson(jsonObject);
    }
}
