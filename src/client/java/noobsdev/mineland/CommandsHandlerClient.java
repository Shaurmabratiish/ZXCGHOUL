package noobsdev.mineland;

import net.fabricmc.api.ClientModInitializer;
import noobsdev.mineland.commands.StartCommand;

public class CommandsHandlerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        StartCommand.register();
    }
}
