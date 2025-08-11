package noobsdev.mineland;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import noobsdev.mineland.commands.StartCommand;
import noobsdev.mineland.http.httpSend;
import noobsdev.mineland.screen.MenuHandler;
import noobsdev.mineland.utilities.Player;

public class MinelandClient implements ClientModInitializer {
    public static long lastRun = 0;
    public static final int DELAY_MS = 60 * 1000;
    @Override
    public void onInitializeClient() {
        Player player = new Player();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!StartCommand.start || client.player == null) return;
            long now = System.currentTimeMillis();
            if (now - lastRun >= DELAY_MS) {
                lastRun = now;
                player.sendMessage("start next loop");
                player.sendCommand("g");
                delay();
            }
        });
    }
    public static void delay() {
        Player pl = new Player();
        new Thread(() -> {
            try {
                Thread.sleep(3000); // 3 секунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            httpSend.sendResponse(MenuHandler.getParseItemsData(pl.getClient().player.currentScreenHandler));

        }).start();
    }

}
