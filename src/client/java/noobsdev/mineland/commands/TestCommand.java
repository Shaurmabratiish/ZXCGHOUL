package noobsdev.mineland.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.*;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.text.Text;
import noobsdev.mineland.http.httpSend;
import noobsdev.mineland.parser.GameMenuParser;
import noobsdev.mineland.parser.ScoreboardParser;
import noobsdev.mineland.utilities.PacketsUtility;
import noobsdev.mineland.utilities.Player;

import java.util.Collection;
import java.util.List;

public class TestCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("test2").executes(context -> {
                MinecraftClient client = MinecraftClient.getInstance();
                Player pl = new Player();
                pl.sendCommand("g");

                new Thread(() -> {
                    try {
                        Thread.sleep(3000); // 3 секунды
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PacketsUtility.clickSlot(21);
                }).start();

                return 1;
            }));
        });
    }
}
