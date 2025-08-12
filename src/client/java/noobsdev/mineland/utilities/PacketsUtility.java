package noobsdev.mineland.utilities;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class PacketsUtility {
    public static void clickSlot(int slotIndex) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.player.currentScreenHandler == null) {
            System.out.println("Игрок или контейнер не готовы");
            return;
        }

        ScreenHandler handler = client.player.currentScreenHandler;
        ClientPlayNetworkHandler connection = client.getNetworkHandler();

        if (connection == null) {
            System.out.println("Нет сетевого соединения");
            return;
        }

        // Получаем текущее значение "transactionId" для контейнера (чтобы сервер принял пакет)
        int syncId = handler.syncId;
        int revision = handler.getRevision(); // или handler.nextRevision (если геттера нет, взять приватным полем через рефлексию)
        int button = 0; // левая кнопка мыши
        SlotActionType actionType = SlotActionType.PICKUP;
        ItemStack cursorStack = handler.getCursorStack();

        // Создаем пустую карту изменений (можно потом заполнить по необходимости)
        Int2ObjectMap<ItemStack> modifiedStacks = new Int2ObjectOpenHashMap<>();

        ClickSlotC2SPacket packet = new ClickSlotC2SPacket(
                syncId,
                revision,
                slotIndex,
                button,
                actionType,
                cursorStack,
                modifiedStacks
        );

        connection.sendPacket(packet);
        System.out.println("Клик по слоту " + slotIndex + " отправлен");
    }
}
