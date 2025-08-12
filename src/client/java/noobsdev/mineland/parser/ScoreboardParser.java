package noobsdev.mineland.parser;

import noobsdev.mineland.utilities.GsonUtility;
import noobsdev.mineland.utilities.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardParser {

    public static List<String> parse() {
        List<String> parseData = new ArrayList<>();
        Player player = new Player();
        parseData.add(GsonUtility.serializeCreativeData("online",player.getCreativeOnline()));
        return parseData;

    }
}
