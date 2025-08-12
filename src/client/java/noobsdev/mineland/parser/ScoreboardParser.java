package noobsdev.mineland.parser;

import noobsdev.mineland.utilities.GsonUtility;
import noobsdev.mineland.utilities.Player;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardParser {

    public static List<String> parse() {
        List<String> parseData = new ArrayList<>();
        Player player = new Player();
        parseData.add(GsonUtility.serializeCreativeData("online",player.getCreativeOnline()));

        parseData.add(GsonUtility.serializeCreativeData("last-timestamp", getTimestamp()));
        return parseData;

    }
    private static String getTimestamp() {
        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime moscowTime = ZonedDateTime.now(moscowZone);
        long unixSeconds = moscowTime.toEpochSecond();
        String res = "";
        return res + unixSeconds;
    }
}
