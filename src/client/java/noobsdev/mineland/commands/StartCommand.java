package noobsdev.mineland.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import noobsdev.mineland.MinelandClient;
import noobsdev.mineland.utilities.Player;

public class StartCommand {
    public static boolean start = false;

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("startbot").executes(context -> {
                Player player = new Player();

                if(!start) {
                    player.sendMessage("Запуск цикла");
                    long now = System.currentTimeMillis();
                    MinelandClient.lastRun = now;
                    player.sendCommand("g");
                    start = true;
                    MinelandClient.delay();
                }else {
                    player.sendMessage("Цикл уже запущен");
                    return 1;
                }

                return 1;
            }));
        });




        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("stop").executes(context -> {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player == null) return 1;
                if (!start) {
                    client.player.sendMessage(Text.of("Цикл не запущен!"));
                    return 1;
                }
                start = false;
                client.player.sendMessage(Text.of("Loop has stopping!"));

                return 1;
            }));
        });
    }
}
