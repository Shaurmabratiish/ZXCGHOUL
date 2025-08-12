package noobsdev.mineland.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import noobsdev.mineland.parser.ScoreboardParser;
import noobsdev.mineland.utilities.PacketsUtility;
import noobsdev.mineland.utilities.Player;

import java.util.List;


public class TestCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("test2").executes(context -> {
                new Thread(() -> {
                    try {
                        PacketsUtility.rightClickNPC(ScoreboardParser.findRublesNPC());
                        Thread.sleep(2000);
                        new Player().sendMessage(ScoreboardParser.findRublesCollected());


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                return 1;
            }));
        });
    }

}
