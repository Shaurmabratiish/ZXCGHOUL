package noobsdev.mineland.utilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;



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
}
