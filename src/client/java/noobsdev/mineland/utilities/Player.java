package noobsdev.mineland.utilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;

import java.util.Collection;

public class Player {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public Player() {

    }

    public void sendMessage(String msg) {
        client.player.sendMessage(Text.of(msg));
    }
    public void sendCommand(String arg) {
        client.player.networkHandler.sendCommand(arg);
    }
    public MinecraftClient getClient() {
        return client;
    }
    public String getCreativeOnline() {
        Scoreboard scoreboard = client.world.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
        if (objective != null) {
            Collection<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(objective);
            for (ScoreboardEntry entry : entries) {
                if(entry.name().getString().contains("Онлайн")) {

                    String input = entry.name().getString();
                    String result = getTextAfterColonWithoutColors(input);
                    new Player().sendMessage("onl: " + result + "\n curr: " + input);
                    return result;
                }

            }
        }
        return null;
    }
    private static String getTextAfterColonWithoutColors(String input) {
        int colonIndex = input.indexOf(':');
        if (colonIndex == -1 || colonIndex + 1 >= input.length()) {
            return "";
        }
        String afterColon = input.substring(colonIndex + 1).trim();
        // Убираем цветовые коды — это символ § и следующий за ним символ
        return afterColon.replaceAll("§.", "");
    }
}
