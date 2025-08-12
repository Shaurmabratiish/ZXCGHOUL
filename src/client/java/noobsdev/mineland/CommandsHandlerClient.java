package noobsdev.mineland;

import net.fabricmc.api.ClientModInitializer;
import noobsdev.mineland.commands.StartCommand;
import noobsdev.mineland.commands.TestCommand;

public class CommandsHandlerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        StartCommand.register();
        TestCommand.register();
    }
}
