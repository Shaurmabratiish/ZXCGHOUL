package noobsdev.mineland;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.decoration.ArmorStandEntity;
import noobsdev.mineland.commands.StartCommand;
import noobsdev.mineland.http.CreateSendHttpsRequets;
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
                CreateSendHttpsRequets.start();
            }
        });

    }

}
