package noobsdev.mineland.parser;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import noobsdev.mineland.utilities.GsonUtility;
import noobsdev.mineland.utilities.Player;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ScoreboardParser {

    public static List<String> parse() {
        List<String> parseData = new ArrayList<>();
        Player player = new Player();
        HashMap<String, String> data = new HashMap<>();
        data.put("online",getCreativeOnline());
        data.put("last-timestamp", getTimestamp());
        data.put("rubles-collected", findRublesCollected());
        parseData.add(GsonUtility.serializeCreativeData(data));

        return parseData;

    }
    private static String getTimestamp() {
        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime moscowTime = ZonedDateTime.now(moscowZone);
        long unixSeconds = moscowTime.toEpochSecond();
        String res = "";
        return res + unixSeconds;
    }
    private static String getCreativeOnline() {
        MinecraftClient client = MinecraftClient.getInstance();
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
    public static String findRublesCollected() {

        MinecraftClient client = MinecraftClient.getInstance();
        Player player = new Player();
        String result = "";
        try {
            Thread.sleep(2000);
            ScreenHandler handler = client.player.currentScreenHandler;
            ItemStack stack = handler.getSlot(13).getStack();
            List<Text> tooltip = stack.getTooltip(
                    player.getClient().player,
                    player.getClient().options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.BASIC
            );
            if (tooltip.isEmpty()) {
                return null;
            }
            String text = tooltip.get(16).getString();
            String[] parts = text.split(" ", 2); // делим на две части по первому пробелу
            result = parts[0];
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Entity findRublesNPC() {
        MinecraftClient client = MinecraftClient.getInstance();
        Player player = new Player();
        if (client.world == null) {
            return null;
        }
        Vec3d targetPos = new Vec3d(-29.534, 86.0, -31.603);
        double maxDistance = 1.0; // радиус поиска

        for (Entity entity : client.world.getEntities()) {
            if (entity.getPos().isInRange(targetPos, maxDistance)) {
                if (entity.getType() != EntityType.ARMOR_STAND) {
                    String rawName = entity.getName().getString();
                    String noColor = rawName.replaceAll("§.", "");
                    player.sendMessage("name: " + noColor);
                    if(noColor.equals("[Правый клик]")) {
                        player.sendMessage("success");
                        return entity;
                    }

                }
            }
        }
        return null;
    }
}
