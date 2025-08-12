package noobsdev.mineland;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.decoration.ArmorStandEntity;
import noobsdev.mineland.commands.StartCommand;
import noobsdev.mineland.http.httpSend;
import noobsdev.mineland.parser.GameMenuParser;
import noobsdev.mineland.parser.ScoreboardParser;
import noobsdev.mineland.utilities.Player;

import java.util.ArrayList;
import java.util.List;

public class MinelandClient implements ClientModInitializer {
    public static long lastRun = 0;
    public static final int DELAY_MS = 60 * 1000;
    public static List<ArmorStandEntity> l = new ArrayList<>();
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

            try {
                httpSend games = new httpSend("https://6898bc8addf05523e55fb2b3.mockapi.io/api/v1/games");
                httpSend creative = new httpSend("https://6898bc8addf05523e55fb2b3.mockapi.io/api/v1/creative");
                pl.sendMessage(games.delete("1"));
                pl.sendMessage(creative.delete("1"));
                pl.sendMessage(games.postArray(GameMenuParser.getParseItemsData(pl.getClient().player.currentScreenHandler)));

                pl.getClient().execute(() -> pl.getClient().player.closeHandledScreen());

                pl.sendMessage(creative.postArray(ScoreboardParser.parse()));


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
