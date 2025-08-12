package noobsdev.mineland.parser;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import noobsdev.mineland.utilities.GsonUtility;
import noobsdev.mineland.utilities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameMenuParser {
    public static List<String> getParseItemsData(ScreenHandler handler) {
        Player player = new Player();
        try {
            int[] slots = {21, 22, 23, 24, 25, 30, 31, 32, 33, 34, 39, 40, 41, 42, 43};
            int position = 0;
            List<String> gamesData = new ArrayList<>();
            for(int iteration : slots) {

                position += 1;
                player.sendMessage("iter: " + position);
                ItemStack stack = handler.getSlot(iteration).getStack();
                if (stack.isEmpty()) {
                    player.sendMessage("§7Пустой слот " + iteration);
                    continue;
                }

                List<Text> tooltip = stack.getTooltip(
                        player.getClient().player,
                        player.getClient().options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.BASIC
                );
                if (tooltip.isEmpty()) {
                    continue;
                }

                String name = tooltip.get(0).getString();
                if (name.isEmpty()) name = "Без названия";
                String author = tooltip.get(1).getString().split(" ", 3)[2];

                int idIndex = tooltip.size() - 5;
                int pointsIndex = tooltip.size() - 4;
                int onlineIndex = tooltip.size() - 3;
                String idText = tooltip.get(idIndex).getString();
                String id = idText.substring(idText.indexOf(":") + 1).trim();
                Integer points = Integer.valueOf(tooltip.get(pointsIndex).getString().replaceAll("\\D+", ""));
                Integer online = Integer.valueOf(tooltip.get(onlineIndex).getString().replaceAll("\\D+", ""));
                player.sendMessage("\n\n id: " + id + "\n name: " + name + "\n online: " + online + "\n points: " + points);
                String serData = GsonUtility.serializeGame(name, author, id, online, points);
                gamesData.add(serData);
            }
            return gamesData;
        }catch (Exception e) {
            player.sendCommand(e.getMessage());
            return null;
        }
    }

}
