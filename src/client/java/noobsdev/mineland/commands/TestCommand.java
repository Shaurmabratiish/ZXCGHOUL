package noobsdev.mineland.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.*;
import net.minecraft.text.Text;
import noobsdev.mineland.utilities.Player;

import java.util.Collection;
import java.util.List;

public class TestCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("scoreboardclient").executes(context -> {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player == null) return 1;
                Player player = new Player();


                Scoreboard scoreboard = client.world.getScoreboard();
                ScoreboardObjective objective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

                if (objective != null) {
                    // Заголовок
                    String title = objective.getDisplayName().getString();
                    player.sendMessage("Заголовок: " + title);
                    Collection<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(objective);
                    for (ScoreboardEntry entry : entries) {
                        if(entry.name().getString().contains("Онлайн")) {
                            player.sendMessage("Онлайн: " + entry.name().getString());
                            break;
                        }
                        player.sendMessage("it");

                    }
                    // Строки скорборда
                }

                return 1;
            }));
        });
    }
}
